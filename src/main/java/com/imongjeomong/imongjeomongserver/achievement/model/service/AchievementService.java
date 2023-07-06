package com.imongjeomong.imongjeomongserver.achievement.model.service;

import com.imongjeomong.imongjeomongserver.dto.AchievementDto;

import java.util.List;

public interface AchievementService {
    List<AchievementDto> getMyAchievementList(Long memberId);

    void setGetState(Long memberId, Long achievementId);
}
