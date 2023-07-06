package com.imongjeomong.imongjeomongserver.achievement.model.service;

import com.imongjeomong.imongjeomongserver.achievement.model.repository.AchievementRepository;
import com.imongjeomong.imongjeomongserver.achievement.model.repository.MyAchievementRepository;
import com.imongjeomong.imongjeomongserver.attraction.model.repository.MyAttractionRepository;
import com.imongjeomong.imongjeomongserver.dto.AchievementDto;
import com.imongjeomong.imongjeomongserver.entity.Achievement;
import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.entity.MyAchievement;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.UnAuthenticationException;
import com.imongjeomong.imongjeomongserver.item.model.repository.ItemRepository;
import com.imongjeomong.imongjeomongserver.item.model.repository.MyItemRepository;
import com.imongjeomong.imongjeomongserver.member.model.repository.MemberRepository;
import com.imongjeomong.imongjeomongserver.review.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final MyAchievementRepository myAchievementRepository;
    private final MemberRepository memberRepository;

    private final MyAttractionRepository myAttractionRepository;
    private final ReviewRepository reviewRepository;
    private final MyItemRepository myItemRepository;

    /**
     * 나의 업적 리스트 가져오기
     */
    @Override
    @Transactional
    public List<AchievementDto> getMyAchievementList(Long memberId) {
        /*
        반환해야 할 정보 : 업적, 업적별 카운트, 경험치, 이름, 보상 받았는지 여부 (금, 은, 동 인지)
            동 - 1, 은 - 3, 금 - 5

        업적 정보
            1. 출석하기
            2. 방문한 관광지 개수
            3. 리뷰 작성하기
            4. 아이템 구매 횟수
         */
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        // 1. 출석하기
        /*
        (출석 카운트는 현재 임시로 넣어놓음 -> 추후 출석 로그 테이블 생성 후 변경 필요)
         */
        Long attendCount = 1L;

        // 2. 방문한 관광지 개수
        Long attractionCount = myAttractionRepository.countByMemberId(memberId);

        // 3. 리뷰 작성 개수
        Long reviewCount = reviewRepository.countByMemberId(memberId);

        // 4. 아이템 구매 횟수
        Long itemCount = myItemRepository.countByMemberId(memberId);

        log.info("관광지={}, 리뷰={}, 아이템={}", attractionCount, reviewCount, itemCount);

        // 업적별 리스트를 반환
        List<Achievement> achievementList = achievementRepository.findAll();
        List<AchievementDto> resultList = new ArrayList<>();

        for (Achievement achievement : achievementList) {
            Optional<MyAchievement> optionalMyAchievement = myAchievementRepository.findByMemberIdAndAchievementId(memberId, achievement.getId());
            MyAchievement myAchievement = null;
            if (optionalMyAchievement.isEmpty()) {
                myAchievement = new MyAchievement();
                myAchievement.setMember(member);
                myAchievement.setAchievement(achievement);
                myAchievement.setGetState(myAchievement.isGetState());
                myAchievementRepository.save(myAchievement);
            } else {
                myAchievement = optionalMyAchievement.get();
            }

            AchievementDto achievementDto = AchievementDto.builder()
                    .achievementId(achievement.getId())
                    .name(achievement.getName())
                    .exp(achievement.getExp())
                    .gold(achievement.getGold())
                    .getState(myAchievement.isGetState())
                    .build();

            if ("출석하기".equals(achievement.getName())) {
                achievementDto.setCount(attendCount);
            } else if ("관광지 방문하기".equals(achievement.getName())) {
                achievementDto.setCount(attractionCount);
            } else if ("리뷰 작성하기".equals(achievement.getName())) {
                achievementDto.setCount(reviewCount);
            } else if ("아이템 구매하기".equals(achievement.getName())) {
                achievementDto.setCount(itemCount);
            }

            resultList.add(achievementDto);
        }

        return resultList;
    }

    /**
     * 업적 보상받기 버튼 클릭시 반영해주는 메서드
     */
    @Override
    @Transactional
    public void setGetState(Long memberId, Long achievementId) {
        // 보상을 받으면 -> 골드 증가, state 상태 세팅
        MyAchievement myAchievement = myAchievementRepository.findByMemberIdAndAchievementId(memberId, achievementId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.ACHIEVEMENT_NOT_FOUND));

        // 이미 보상을 받았다면 예외 발생
        if (myAchievement.isGetState()) throw new CommonException(CustomExceptionStatus.ACHIEVEMENT_ALREADY_GET);

        // 골드 증가
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));
        member.setGold(member.getGold() + myAchievement.getAchievement().getGold());

        myAchievement.setGetState(true);
    }
}
