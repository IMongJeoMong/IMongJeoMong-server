package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.dto.MyAttractionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MyAttraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    private int count;
    private LocalDateTime visitTime;

    public MyAttractionDTO toMyAttractionDto(){
        return MyAttractionDTO.builder()
                .myAttractionId(this.getId())
                .memberId(this.memberId)
                .attractionId(this.getAttraction().getId())
                .name(this.getAttraction().getName())
                .address(this.getAttraction().getAddress())
                .lat(this.attraction.getLat())
                .lng(this.attraction.getLng())
                .description(this.getAttraction().getDescription())
                .contentTypeId(this.getAttraction().getContentTypeId())
                .imagePath(this.getAttraction().getImagePath())
                .tel(this.getAttraction().getTel())
                .gold(this.getAttraction().getGold())
                .exp(this.getAttraction().getExp())
                .count(this.getCount())
                .visitTime(this.getVisitTime())
                .build();
    }

}
