package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.entity.common.EditTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
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

    public void privateInformationProcessing(){
        this.password = "PRIVATE";
    }

    public void modifyValue(Member member){
        Optional.ofNullable(member.getNickname()).ifPresent(
                (nickname) -> this.nickname = nickname
        );

        Optional.ofNullable(member.getBirth()).ifPresent(
                (birth) -> this.birth = birth
        );

        Optional.ofNullable(member.getGender()).ifPresent(
                (gender) -> this.gender = gender
        );

        Optional.ofNullable(member.getSidoCode()).ifPresent(
                (sidoCode) -> this.sidoCode = sidoCode
        );

        Optional.ofNullable(member.getMyMong()).ifPresent(
                (myMong) -> this.myMong = myMong
        );
    }
}
