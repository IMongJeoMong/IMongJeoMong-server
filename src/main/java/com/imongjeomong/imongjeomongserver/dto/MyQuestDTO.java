package com.imongjeomong.imongjeomongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class MyQuestDTO {

    private Long id;
    private String name;
    private int exp;
    private int gold;
    private Boolean clearFlag;
    private Boolean rewardFlag;

}
