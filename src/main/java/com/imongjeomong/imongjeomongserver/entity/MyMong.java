package com.imongjeomong.imongjeomongserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter
@Entity
public class MyMong {
    @Id
    private Long id;

}
