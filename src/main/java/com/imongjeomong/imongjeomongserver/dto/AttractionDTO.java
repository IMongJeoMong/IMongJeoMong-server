package com.imongjeomong.imongjeomongserver.dto;


import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttractionDTO {

    private Long id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private String description;

    private int contentTypeId;
    private String imagePath;
    private String tel;
    private int sidoCode;

    private int gold;
    private int exp;

    private double distance;
}
