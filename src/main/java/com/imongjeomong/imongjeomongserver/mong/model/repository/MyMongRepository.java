package com.imongjeomong.imongjeomongserver.mong.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyMong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyMongRepository extends JpaRepository<MyMong, Long> {
    @Query("SELECT m from MyMong m JOIN FETCH m.mong")
    List<MyMong> findAllByMemberId(Long memberId);

    @Query("SELECT m from MyMong m JOIN FETCH m.mong WHERE m.id = :myMongId")
    Optional<MyMong> findById(Long myMongId);

}
