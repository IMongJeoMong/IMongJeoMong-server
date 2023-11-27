package com.imongjeomong.imongjeomongserver.attraction.model.service;

import com.imongjeomong.imongjeomongserver.attraction.model.repository.AttractionRepository;
import com.imongjeomong.imongjeomongserver.attraction.model.repository.MyAttractionRepository;
import com.imongjeomong.imongjeomongserver.dto.AttractionDTO;
import com.imongjeomong.imongjeomongserver.dto.MyAttractionDTO;
import com.imongjeomong.imongjeomongserver.entity.Attraction;
import com.imongjeomong.imongjeomongserver.entity.MyAttraction;
import com.imongjeomong.imongjeomongserver.entity.Review;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.review.model.repository.ReviewRepository;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final MyAttractionRepository myAttractionRepository;
    private final ReviewRepository reviewRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Page<AttractionDTO> getAttractionList(Map<String, Object> paramMap, Pageable pageable) {
        Double lat = Double.parseDouble(paramMap.get("lat").toString());
        Double lng = Double.parseDouble(paramMap.get("lng").toString());
        String keyword = paramMap.get("keyword").toString();
        return attractionRepository.findAttractionByCondition(keyword, lat, lng, pageable);
    }

    @Override
    public AttractionDTO getAttractionInfo(Long attractionId) {
        Attraction attraction = attractionRepository.findById(attractionId).
                orElseThrow(() -> new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND));

        AttractionDTO attractionDTO = AttractionDTO.builder()
                .id(attraction.getId())
                .name(attraction.getName())
                .address(attraction.getAddress())
                .lat(attraction.getLat())
                .lng(attraction.getLng())
                .description(attraction.getDescription())
                .contentTypeId(attraction.getContentTypeId())
                .imagePath(attraction.getImagePath())
                .tel(attraction.getTel())
                .sidoCode(attraction.getSidoCode())
                .gold(attraction.getGold())
                .exp(attraction.getExp()).build();
        return attractionDTO;
    }

    @Transactional
    @Override
    public List<MyAttractionDTO> getMyAttractionList(HttpServletRequest request, Pageable pageable) {
        List<MyAttractionDTO> myAttractionList = new ArrayList<>();

        Long memberId = jwtUtil.getMemberId(getAccessToken(request));
        myAttractionRepository.findAllByMemberId(memberId, pageable).stream().forEach(
                myAttraction -> {
                    MyAttractionDTO myAttractionDTO = myAttraction.toMyAttractionDto();
                    List<Review> reviewList = reviewRepository.findByMyAttractionIdAndMemberId(myAttraction.getId(), memberId);
                    if (reviewList.size() > 0) {
                        myAttractionDTO.setReviewId(reviewList.get(0).getId());
                        myAttractionDTO.setWrote(true);
                    } else {
                        myAttractionDTO.setWrote(false);
                    }
                    myAttractionList.add(myAttractionDTO);
                }
        );
        return myAttractionList;
    }

    @Transactional
    @Override
    public void visitAttraction(Long attractionId, HttpServletRequest request, Map<String, Object> paramMap) {
        String accessToken = getAccessToken(request);
        Long memberId = jwtUtil.getMemberId(accessToken);

        Double lat = Double.parseDouble(paramMap.get("lat").toString());
        Double lng = Double.parseDouble(paramMap.get("lng").toString());
        AttractionDTO attractionDTO = attractionRepository.findByAttractionId(attractionId, lat, lng).orElseThrow(
                () -> new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND)
        );
        if (attractionDTO.getDistance() > 30000) {
            throw new CommonException(CustomExceptionStatus.ATTRACTION_SEARCH_FAILED);
        }

        myAttractionRepository.findByMemberIdAndAttractionId(memberId, attractionId)
                .ifPresentOrElse(
                        value -> { // 이미 방문한 적 있을 경우
                            LocalDateTime visitedTime = value.getVisitTime();

                            Duration duration = Duration.between(visitedTime, LocalDateTime.now());
                            if (duration.toHours() < 24) {
                                throw new CommonException(CustomExceptionStatus.ATTRACTION_NOT_A_DAY_PASSED);
                            } else {
                                value.setVisitTime(LocalDateTime.now());
                                myAttractionRepository.save(value);
                            }
                        },
                        () -> { // 첫 방문일 경우
                            MyAttraction visitAttraction = new MyAttraction();
                            visitAttraction.setMemberId(memberId);

                            attractionRepository.findById(attractionId).
                                    ifPresentOrElse(
                                            visitAttraction::setAttraction,
                                            () -> {
                                                throw new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND);
                                            }
                                    );
                            visitAttraction.setCount(1);
                            visitAttraction.setVisitTime(LocalDateTime.now());
                            myAttractionRepository.save(visitAttraction);
                        }
                );
    }

    @Transactional
    @Override
    public void visitAttractionByLog(Long attractionId, HttpServletRequest request, Map<String, Object> paramMap) {
        // 유저 정보 파싱
        String accessToken = getAccessToken(request);
        Long memberId = jwtUtil.getMemberId(accessToken);

        // 30m 이내가 아니라면 방문 실패 예외처리
        Double lat = Double.parseDouble(paramMap.get("lat").toString());
        Double lng = Double.parseDouble(paramMap.get("lng").toString());
        AttractionDTO attractionDTO = attractionRepository.findByAttractionId(attractionId, lat, lng).orElseThrow(
                () -> new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND)
        );
        if (attractionDTO.getDistance() > 30000) {
            throw new CommonException(CustomExceptionStatus.ATTRACTION_SEARCH_FAILED);
        }

        // 방문처리 - DB에 로그 추가
        MyAttraction myAttraction = new MyAttraction();
        myAttraction.setMemberId(memberId);
        Attraction attraction = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND));

        // 하루가 안지났으면 예외처리
        List<MyAttraction> myAttraction1 = myAttractionRepository.findMyAttractionByVisitTime(attractionId, memberId);
        if (myAttraction1.size() != 0) {
            MyAttraction cur = myAttraction1.get(0);
            LocalDateTime visitedTime = cur.getVisitTime();

            Duration duration = Duration.between(visitedTime, LocalDateTime.now());
            if (duration.toHours() < 24) {
                throw new CommonException(CustomExceptionStatus.ATTRACTION_NOT_A_DAY_PASSED);
            }
        }

        // 로그 저장
        myAttraction.setVisitTime(LocalDateTime.now());
        myAttraction.setCount(1);
        myAttraction.setAttraction(attraction);
        myAttractionRepository.save(myAttraction);
    }

    /* 헤더 Authorization 파싱 */
    private static String getAccessToken(HttpServletRequest request) {
        String accessToken = "";
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
        } catch (NullPointerException e) {
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }
        return accessToken;
    }
}
