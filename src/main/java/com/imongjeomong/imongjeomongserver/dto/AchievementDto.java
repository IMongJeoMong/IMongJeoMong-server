package com.imongjeomong.imongjeomongserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AchievementDto {
    private Long achievementId;
    private String name;
    private int gold;
    private int exp;

    private Long count;
    private boolean getState;
}
