package com.imongjeomong.imongjeomongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class MyMongDto {

    private Long myMongId;
    private Long mongId;
    private String name;
    private int level;
    private int exp;
    private String imagePath;
    private String description;

}
