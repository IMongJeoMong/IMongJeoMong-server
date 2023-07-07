package com.imongjeomong.imongjeomongserver.review.model.service;

import com.imongjeomong.imongjeomongserver.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {
    void saveReview(ReviewDto reviewDto, MultipartFile image) throws IOException;

    List<ReviewDto> getReviewList(Long attractionId);

    void updateReview(Long memberId, Long reviewId, MultipartFile image, String content) throws IOException;

    void deleteReview(Long memberId, Long reviewId);
}
