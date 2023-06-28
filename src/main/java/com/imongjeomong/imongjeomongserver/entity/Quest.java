package com.imongjeomong.imongjeomongserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quest {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private int exp;
    private int gold;
}
