package com.imongjeomong.imongjeomongserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Attraction {

    @Id
    private Long id;

    /**
     * 공공데이터 분석 후 필드 추가 필요함
     */

    private String name;
    private String address;
    private Double lat;
    private Double lng;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int contentTypeId;
    private String imagePath;
    private String tel;
    private int sidoCode;
}
