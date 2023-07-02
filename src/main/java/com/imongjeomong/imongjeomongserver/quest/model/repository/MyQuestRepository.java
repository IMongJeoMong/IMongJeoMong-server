package com.imongjeomong.imongjeomongserver.quest.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyQuestRepository extends JpaRepository<MyQuest, Long> {

    List<MyQuest> findAllByMemberId(Long memberId);
}
