package com.imongjeomong.imongjeomongserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter @Setter
@Entity
public class Member {

    @Id
    private Long id;

    private String email;
    private String password;
    @Column(length = 50)
    private String nickname;

    private LocalDate birth;

    @Column(length = 1)
    private String gender;
    private int sidoCode;
    private int gold;
    private int myCharacterId;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime removeTime;

}
