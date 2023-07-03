package com.imongjeomong.imongjeomongserver.quest.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyQuestRepository extends JpaRepository<MyQuest, Long> {

    @Query("SELECT q FROM MyQuest q JOIN FETCH q.quest WHERE q.memberId = :memberId")
    List<MyQuest> findAllByMemberId(Long memberId);
}
