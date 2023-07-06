package com.imongjeomong.imongjeomongserver.background.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyBackground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyBackgroundRepository extends JpaRepository<MyBackground, Long> {

    @Query("SELECT b FROM MyBackground b JOIN FETCH b.background WHERE b.member.id = :memberId")
    List<MyBackground> findAllByMemberId(Long memberId);

    @Query("SELECT b FROM MyBackground b JOIN FETCH b.background " +
            "WHERE b.member.id = :memberId " +
            "  AND b.id = :myBackgroundId")
    Optional<MyBackground> findByIdAndMemberId(Long myBackgroundId, Long memberId);
}
