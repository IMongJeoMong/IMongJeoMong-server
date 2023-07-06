package com.imongjeomong.imongjeomongserver.review.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select count(r) from Review r where r.member.id = :member_id")
    Long countByMemberId(@Param("member_id") Long memberid);

    @Query("select r from Review r join fetch r.member where r.attraction.id = :attractionId")
    List<Review> findByAttractionId(@Param("attractionId") Long attractionId);
}
