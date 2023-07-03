package com.imongjeomong.imongjeomongserver.achievement.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyAchievementRepository extends JpaRepository<MyAchievement, Long> {
}
