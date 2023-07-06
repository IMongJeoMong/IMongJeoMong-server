package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Background {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private String imagePath;

    public BackgroundDto toBackgroundDto(){
        return BackgroundDto.builder()
                .backgroundId(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .build();
    }
}
