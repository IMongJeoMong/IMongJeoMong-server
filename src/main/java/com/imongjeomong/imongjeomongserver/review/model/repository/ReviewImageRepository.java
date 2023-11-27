package com.imongjeomong.imongjeomongserver.review.model.repository;

import com.imongjeomong.imongjeomongserver.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findByReviewId(Long reviewId);

}
