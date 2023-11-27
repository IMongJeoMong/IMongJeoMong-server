package com.imongjeomong.imongjeomongserver.attraction.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyAttraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyAttractionRepository extends JpaRepository<MyAttraction, Long> {

    @Query(value = "SELECT a from MyAttraction a JOIN FETCH a.attraction WHERE a.memberId = :memberId",
            countQuery = "SELECT COUNT(*) From MyAttraction a")
    Page<MyAttraction> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<MyAttraction> findByMemberIdAndAttractionId(Long attractionId, Long memberId);

    @Query("select m from MyAttraction m where m.attraction.id = :attractionId and m.memberId = :memberId order by m.visitTime desc")
    List<MyAttraction> findMyAttractionByVisitTime(@Param("attractionId") Long attractionId, @Param("memberId") Long memberId);

    @Query("select count(m) from MyAttraction m where m.memberId = :member_id")
    Long countByMemberId(@Param("member_id") Long memberId);
}
