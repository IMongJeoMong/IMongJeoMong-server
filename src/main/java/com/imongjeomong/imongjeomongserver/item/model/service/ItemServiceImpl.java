package com.imongjeomong.imongjeomongserver.item.model.service;

import com.imongjeomong.imongjeomongserver.dto.ItemDto;
import com.imongjeomong.imongjeomongserver.dto.MyItemDto;
import com.imongjeomong.imongjeomongserver.entity.Item;
import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.entity.MyItem;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.UnAuthenticationException;
import com.imongjeomong.imongjeomongserver.item.model.repository.ItemRepository;
import com.imongjeomong.imongjeomongserver.item.model.repository.MyItemRepository;
import com.imongjeomong.imongjeomongserver.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MyItemRepository myItemRepository;
    private final MemberRepository memberRepository;

    /**
     * 내가 보유한 아이템 목록만 반환
     */
    @Override
    @Transactional
    public List<ItemDto> getMyItemList(Long memberId) {
        List<MyItem> myItem = myItemRepository.findByMemberId(memberId);

        List<ItemDto> itemList = new ArrayList<>();
        for (MyItem m : myItem) {
            Item curItem = m.getItem();
            ItemDto itemDto = curItem.toItemDto();
            itemDto.setOwn(true);
            itemDto.setMyItemId(m.getId());
            itemList.add(itemDto);
        }

        return itemList;
    }

    /**
     * 전체 아이템 리스트 반환
     */
    @Override
    public List<ItemDto> getItemList() {
        List<Item> itemList = itemRepository.findAll();

        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemList) {
            itemDtoList.add(item.toItemDto());
        }

        return itemDtoList;
    }

    /**
     * 전체 아이템 리스트에 보유 여부를 표시해 반환
     */
    @Override
    @Transactional
    public List<ItemDto> getOwnItemList(Long memberId) {
        List<ItemDto> myItemList = getMyItemList(memberId);
        List<ItemDto> itemList = getItemList();

        Map<Long, Long> myItemMap = new HashMap<>();
        for (ItemDto itemDto : myItemList) {
            myItemMap.put(itemDto.getItemId(), itemDto.getMyItemId());
        }

        for (ItemDto itemDto : itemList) {
            if (myItemMap.containsKey(itemDto.getItemId())) {
                itemDto.setOwn(true);
                itemDto.setMyItemId(myItemMap.get(itemDto.getItemId()));
            }
        }

        return itemList;
    }

    @Override
    public ItemDto getItemInfo(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.ITEM_NOT_FOUND));
        return item.toItemDto();
    }

    public MyItemDto getMyItemById(Long myItemId) {
        MyItem myItem = myItemRepository.findById(myItemId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.ITEM_NOT_FOUND));
        return myItem.toMyItemDto();
    }

    @Override
    public MyItemDto getSelectedItemById(Long selectedItemId) {
        MyItem myItem = myItemRepository.findById(selectedItemId).orElse(null);
        if (myItem != null) return myItem.toMyItemDto();
        return null;
    }

    @Override
    @Transactional
    public ItemDto buyItem(Long memberId, Long itemId) {
        /*
        1. 아이템 구매시 해당 유저의 골드를 검사한다 (골드 부족 시 예외 발생)
        2. 중복 구매 방지 (같은 아이템을 이미 보유하고 있다면 예외 발생)
        3. 골드 소모 후 MyItem 테이블에 아이템 추가
         */

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.ITEM_NOT_FOUND));

        // 1. 골드 부족 검증 로직
        if (item.getPrice() > member.getGold()) {
            throw new CommonException(CustomExceptionStatus.MEMBER_NOT_ENOUGH_GOLD);
        }

        // 2. 중복 구매 방지 로직
        myItemRepository.findByItemIdAndMemberId(itemId, memberId).ifPresent(i -> {
            log.error("{} 중복 구매 시도", member.getEmail());
            throw new CommonException(CustomExceptionStatus.ITEM_DUPLICATE);
        });


        // 3. 골드 소모 후 아이템 추가
        member.setGold(member.getGold() - item.getPrice());

        MyItem buyItem = new MyItem();
        buyItem.setItem(item);
        buyItem.setMember(member);

        myItemRepository.save(buyItem);

        return item.toItemDto();
    }
}
