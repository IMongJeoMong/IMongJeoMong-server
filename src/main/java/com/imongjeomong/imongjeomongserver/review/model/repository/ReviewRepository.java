package com.imongjeomong.imongjeomongserver.review.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select count(r) from Review r where r.member.id = :member_id")
    Long countByMemberId(@Param("member_id") Long memberid);
}
