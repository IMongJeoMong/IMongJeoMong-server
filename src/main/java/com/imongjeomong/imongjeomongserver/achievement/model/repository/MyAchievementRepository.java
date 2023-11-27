package com.imongjeomong.imongjeomongserver.achievement.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyAchievementRepository extends JpaRepository<MyAchievement, Long> {
    Optional<MyAchievement> findByMemberIdAndAchievementId(Long memberId, Long achievementId);
}
