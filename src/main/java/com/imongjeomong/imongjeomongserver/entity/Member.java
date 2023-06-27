package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.entity.common.EditTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    @Column(length = 50)
    private String nickname;

    private LocalDate birth;
    @Column(length = 1)
    private String gender;
    private int sidoCode;
    private int gold;

    @OneToOne
    @JoinColumn(name = "my_mong_id")
    private MyMong myMong;

    @Embedded
    private EditTime editTime;

}
