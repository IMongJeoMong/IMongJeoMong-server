package com.imongjeomong.imongjeomongserver.item.model.service;

import com.imongjeomong.imongjeomongserver.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getMyItemList(Long memberId);

    List<ItemDto> getItemList();

    ItemDto buyItem(Long memberId, Long itemId);

    List<ItemDto> getOwnItemList(Long memberId);

    ItemDto getItemInfo(Long itemId);
}
