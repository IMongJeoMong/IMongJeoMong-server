package com.imongjeomong.imongjeomongserver.review.controller;


import com.imongjeomong.imongjeomongserver.dto.ReviewDto;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.quest.model.service.QuestService;
import com.imongjeomong.imongjeomongserver.response.CommonResponse;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import com.imongjeomong.imongjeomongserver.review.model.service.ReviewService;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;
    private final QuestService questServiceImpl;
    private final JwtUtil jwtUtil;

    /**
     * review 작성 (C)
     */
    @Transactional
    @PostMapping("/review")
    public CommonResponse writeReview(HttpServletRequest request,
                                      @ModelAttribute MultipartFile image,
                                      Long attractionId, String content) throws IOException {
        String accessToken = "";
        Long memberId = null;
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
            memberId = jwtUtil.getMemberId(accessToken);
        } catch (NullPointerException e) {
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }

        // 입력값을 reviewDto로 변환 후 서비스로 전달
        ReviewDto reviewDto = ReviewDto.builder()
                .memberId(memberId)
                .attractionId(attractionId)
                .content(content)
                .build();

        reviewService.saveReview(reviewDto, image);

        // 리뷰 작성 퀘스트
        questServiceImpl.writeReviewQuest(memberId);

        return new CommonResponse(200, "리뷰 저장 성공");
    }

    @PostMapping("/review/{myAttractionId}")
    public CommonResponse writeReviewByMyAttractionId(HttpServletRequest request, @PathVariable Long myAttractionId,
                                                      @ModelAttribute MultipartFile image, Long attractionId, String content) throws IOException {
        String accessToken = "";
        Long memberId = null;
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
            memberId = jwtUtil.getMemberId(accessToken);
        } catch (NullPointerException e) {
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }

        // 입력값을 reviewDto로 변환 후 서비스로 전달
        ReviewDto reviewDto = ReviewDto.builder()
                .memberId(memberId)
                .attractionId(attractionId)
                .content(content)
                .myAttractionId(myAttractionId)
                .build();

        reviewService.saveReviewByMyAttractionId(reviewDto, image);

        // 리뷰 작성 퀘스트
        questServiceImpl.writeReviewQuest(memberId);

        return new CommonResponse(200, "리뷰 저장 성공");
    }


    /**
     * review 가져오기 (R)
     */
    @GetMapping("/review/{attractionId}")
    public DataResponse<?> getReviewList(@PathVariable Long attractionId) {
        List<ReviewDto> reviewDtoList = reviewService.getReviewList(attractionId);

        DataResponse<List<ReviewDto>> response = new DataResponse<>(200, "리뷰 리스트 조회 성공");
        response.setData(reviewDtoList);

        return response;
    }

    @GetMapping("/review/id/{reviewId}")
    public DataResponse<?> getReview(@PathVariable Long reviewId) {
        ReviewDto review = reviewService.getReview(reviewId);

        DataResponse<ReviewDto> response = new DataResponse<>(200, "리뷰 리스트 조회 성공");
        response.setData(review);
        return response;
    }


    /**
     * review 수정 (U)
     */
    @PatchMapping("/review/{reviewId}")
    public CommonResponse updateReview(HttpServletRequest request,
                                       @ModelAttribute MultipartFile image,
                                       @PathVariable Long reviewId, String content) throws IOException {
        Long memberId = jwtUtil.getMemberId(jwtUtil.getAccessToken(request));

        reviewService.updateReview(memberId, reviewId, image, content);

        return new CommonResponse(201, "리뷰 수정 성공");
    }


    /**
     * review 삭제 (D)
     */
    @DeleteMapping("/review/{reviewId}")
    public CommonResponse deleteReview(HttpServletRequest request, @PathVariable Long reviewId) {
        Long memberId = jwtUtil.getMemberId(jwtUtil.getAccessToken(request));

        reviewService.deleteReview(memberId, reviewId);
        return new CommonResponse(201, "리뷰 삭제 성공");
    }

}
