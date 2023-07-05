package com.imongjeomong.imongjeomongserver.dto;

import com.imongjeomong.imongjeomongserver.entity.Mong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class MyMongDto {

    private Long myMongId;
    private Mong mong;
    private int level;
    private int exp;

}
