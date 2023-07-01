package com.imongjeomong.imongjeomongserver.entity;

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
}
