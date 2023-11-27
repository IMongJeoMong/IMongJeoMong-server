package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.dto.MyItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public MyItemDto toMyItemDto(){
        return MyItemDto.builder()
                .myItemId(this.id)
                .itemId(this.item.getId())
                .name(this.item.getName())
                .price(this.item.getPrice())
                .shopImagePath(this.item.getShopImagePath())
                .charImagePath(this.item.getCharImagePath())
                .build();
    }
}
