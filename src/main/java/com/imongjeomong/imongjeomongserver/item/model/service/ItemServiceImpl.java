package com.imongjeomong.imongjeomongserver.item.model.service;

import com.imongjeomong.imongjeomongserver.dto.ItemDto;
import com.imongjeomong.imongjeomongserver.entity.Item;
import com.imongjeomong.imongjeomongserver.entity.MyItem;
import com.imongjeomong.imongjeomongserver.item.model.repository.ItemRepository;
import com.imongjeomong.imongjeomongserver.item.model.repository.MyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MyItemRepository myItemRepository;

    @Override
    @Transactional
    public List<ItemDto> getMyItemList(Long memberId) {
        List<MyItem> myItem = myItemRepository.findByMemberId(memberId);

        List<ItemDto> itemList = new ArrayList<>();
        for (MyItem m : myItem) {
            Item curItem = m.getItem();
            itemList.add(ItemDto.builder()
                    .itemId(curItem.getId())
                    .name(curItem.getName())
                    .price(curItem.getPrice())
                    .imagePath(curItem.getImagePath()).build());
        }

        return itemList;
    }

    @Override
    public List<ItemDto> getItemList() {
        List<Item> itemList = itemRepository.findAll();

        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemList) {
            itemDtoList.add(ItemDto.builder()
                    .itemId(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .imagePath(item.getImagePath())
                    .build());
        }

        return itemDtoList;
    }
}
