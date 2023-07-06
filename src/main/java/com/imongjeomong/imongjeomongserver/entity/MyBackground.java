package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.dto.MyBackgroundDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor
public class MyBackground {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "background_id")
    private Background background;

    public MyBackgroundDto toMyBackgroundDto(){
        return MyBackgroundDto.builder()
                .myBackgroundId(this.getId())
                .backgroundId(this.background.getId())
                .name(this.getBackground().getName())
                .price(this.getBackground().getPrice())
                .imagePath(this.getBackground().getImagePath())
                .build();
    }
}
