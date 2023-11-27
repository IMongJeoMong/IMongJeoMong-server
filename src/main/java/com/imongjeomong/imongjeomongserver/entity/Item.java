package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.dto.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private String shopImagePath;
    private String charImagePath;

    public ItemDto toItemDto() {
        return ItemDto.builder()
                .itemId(this.getId())
                .shopImagePath(this.getShopImagePath())
                .charImagePath(this.getCharImagePath())
                .name(this.getName())
                .price(this.getPrice()).build();
    }
}
