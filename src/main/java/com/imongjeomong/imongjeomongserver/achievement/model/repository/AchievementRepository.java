package com.imongjeomong.imongjeomongserver.achievement.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
