package com.imongjeomong.imongjeomongserver.attraction.model.repository;

import com.imongjeomong.imongjeomongserver.dto.MyAttractionDTO;
import com.imongjeomong.imongjeomongserver.entity.MyAttraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyAttractionRepository extends JpaRepository<MyAttraction, Long> {

    Page<MyAttractionDTO> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<MyAttraction> findByMemberIdAndAttractionId(Long attractionId, Long memberId);
}
