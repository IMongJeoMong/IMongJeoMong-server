package com.imongjeomong.imongjeomongserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDto {
    private Long itemId;
    private String name;
    private int price;
    private String imagePath;
}
