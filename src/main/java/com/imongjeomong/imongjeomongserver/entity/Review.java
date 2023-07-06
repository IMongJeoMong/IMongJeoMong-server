package com.imongjeomong.imongjeomongserver.entity;


import com.imongjeomong.imongjeomongserver.entity.common.EditTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
    private String content;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "review")
    private List<ReviewImage> reviewImageList = new ArrayList<>();

    @Embedded
    private EditTime editTime;
}
