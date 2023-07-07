package com.imongjeomong.imongjeomongserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private boolean isWrote;
    private int count;
    private String visitTime;
}
