package com.imongjeomong.imongjeomongserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class MyBackgroundDto {
    private Long myBackgroundId;
    private String name;
    private int price;
    private String imagePath;
}
