package com.imongjeomong.imongjeomongserver.mong.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyMong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyMongRepository extends JpaRepository<MyMong, Long> {
    @Query("SELECT m from MyMong m join fetch m.mong")
    List<MyMong> findAllByMemberId(Long memberId);

}
