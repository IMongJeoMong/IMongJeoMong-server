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
    private Long myItemId;
    private String name;
    private int price;
    private String shopImagePath;
    private String charImagePath;
    private boolean isOwn;
}
