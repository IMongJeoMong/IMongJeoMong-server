package com.imongjeomong.imongjeomongserver.opendata.tasu;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TASU")
@Getter
@Setter
public class Tasu {

    @Id
    @GeneratedValue
    private Long id;

    private Double lat;
    private Double lng;
    private String kioskId;
    private String address;
}
