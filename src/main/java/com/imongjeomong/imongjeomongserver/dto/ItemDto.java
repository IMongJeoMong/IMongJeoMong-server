package com.imongjeomong.imongjeomongserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long itemId;
    private String name;
    private String price;
    private String imagePath;
}
