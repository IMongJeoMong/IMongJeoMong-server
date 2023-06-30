package com.imongjeomong.imongjeomongserver.item.model.service;

import com.imongjeomong.imongjeomongserver.dto.ItemDto;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MyItemRepository myItemRepository;
    private final MemberRepository memberRepository;

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

    @Override
    @Transactional
    public void buyItem(Long memberId, Long itemId) {
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

        // 2. 중복 구매 방지
        myItemRepository.findByItem(item).ifPresent(i -> {
            throw new CommonException(CustomExceptionStatus.ITEM_NOT_FOUND);
        });


        // 3. 골드 소모 후 아이템 추가
        member.setGold(member.getGold() - item.getPrice());

        MyItem buyItem = new MyItem();
        buyItem.setItem(item);
        buyItem.setMember(member);

        myItemRepository.save(buyItem);
    }
}
