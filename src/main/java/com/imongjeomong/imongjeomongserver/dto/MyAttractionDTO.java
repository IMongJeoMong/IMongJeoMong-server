package com.imongjeomong.imongjeomongserver.dto;

import com.imongjeomong.imongjeomongserver.entity.Attraction;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
@AllArgsConstructor
public class MyAttractionDTO {

    private Long myAttractionId;
    private Long memberId;
    private Long attractionId;
    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private String description;
    private int contentTypeId;
    private String imagePath;
    private String tel;
    private int gold;
    private int exp;

    private int count;
    private LocalDateTime visitTime;
}
