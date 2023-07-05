package com.imongjeomong.imongjeomongserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter
@ToString
public class MyItemDto {
    private Long myItemId;
    private Long memberId;
    private ItemDto itemDto;
}
