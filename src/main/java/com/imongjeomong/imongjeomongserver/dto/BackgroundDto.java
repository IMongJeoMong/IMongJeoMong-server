package com.imongjeomong.imongjeomongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class BackgroundDto {
    private Long backgroundId;
    private String name;
    private int price;
    private String imagePath;
}
