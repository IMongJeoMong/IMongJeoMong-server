package com.imongjeomong.imongjeomongserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class MyItemDto {
    private Long myItemId;
    private Long memberId;
    private ItemDto itemDto;
}
