package com.imongjeomong.imongjeomongserver.review.controller;


import com.imongjeomong.imongjeomongserver.dto.ReviewDto;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.response.CommonResponse;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import com.imongjeomong.imongjeomongserver.review.model.service.ReviewService;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    /**
     * review 작성 (C)
     */
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

        return new CommonResponse(200, "리뷰 저장 성공");
    }

    /**
     * review 가져오기 (R)
     */
    @GetMapping("/review/{attractionId}")
    private DataResponse<List<ReviewDto>> getReviewList(@PathVariable Long attractionId) {
        List<ReviewDto> reviewDtoList = reviewService.getReviewList(attractionId);

        DataResponse<List<ReviewDto>> response = new DataResponse<>(200, "리뷰 리스트 조회 성공");
        response.setData(reviewDtoList);

        return response;
    }

    /**
     * review 수정 (U)
     */

    /**
     * review 삭제 (D)
     */
}
