package com.imongjeomong.imongjeomongserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TASU")
@Getter
@Setter
@ToString
public class Tasu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lat;
    private Double lng;
    private String kioskId;
    private String address;
}
