package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.dto.MyMongDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Data
@Entity
public class MyMong {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "mong_id")
    private Mong mong;

    @JoinColumn(name = "member_id")
    private Long memberId;

    private int level;
    private int exp;

    public MyMongDto toMyMongDto() {
        int percent = (this.exp % 1000) * 100 / 1000;
        return MyMongDto.builder()
                .myMongId(this.getId())
                .mongId(this.mong.getId())
                .name(this.getMong().getName())
                .level(this.getLevel())
                .exp(this.getExp())
                .expPercent(percent)
                .imagePath(this.getMong().getImagePath())
                .description(this.getMong().getDescription())
                .build();
    }
}
