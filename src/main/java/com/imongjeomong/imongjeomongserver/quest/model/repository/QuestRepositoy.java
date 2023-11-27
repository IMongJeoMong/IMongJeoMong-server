package com.imongjeomong.imongjeomongserver.quest.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepositoy extends JpaRepository<Quest, Long> {
}
