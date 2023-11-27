package com.imongjeomong.imongjeomongserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Attraction {

    @Id
    private Long id;
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

    private int gold;   // 방문시 획득 골드
    private int exp;    // 방문시 획득 경험치
}
