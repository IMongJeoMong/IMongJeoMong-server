package com.imongjeomong.imongjeomongserver.entity;

import com.imongjeomong.imongjeomongserver.entity.common.EditTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Getter @Setter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "selected_mong_id")
    private MyMong selectedMong;

    @JoinColumn(name = "selected_item_id")
    private Long selectedItemId;

    @OneToOne
    @JoinColumn(name = "selected_background_id")
    private MyBackground selectedBackground;

    @Embedded
    private EditTime editTime;

    public void privateInformationProcessing(){
        this.password = "PRIVATE";
    }

    public void modifyValue(Map<String, Object> paramMap){
        if (paramMap.containsKey("nickname"))this.nickname = (String) paramMap.get("nickname");
        if (paramMap.containsKey("birth")) this.birth = LocalDate.parse(paramMap.get("birth").toString());
        if (paramMap.containsKey("gender")) this.gender = (String) paramMap.get("gender");
        if (paramMap.containsKey("sidoCode")) this.sidoCode = (int) paramMap.get("sidoCode");
        if (paramMap.containsKey("gold")) this.gold = (int) paramMap.get("gold");
        if (paramMap.containsKey("updateTime")) this.editTime.setUpdateTime((LocalDateTime) paramMap.get("updateTime"));
        if (paramMap.containsKey("selectedMong")) this.selectedMong = (MyMong) paramMap.get("selectedMong");
        if (paramMap.containsKey("selectedItemId")) this.selectedItemId = (Long) paramMap.get("selectedItemId");
        if (paramMap.containsKey("selectedBackground")) this.selectedBackground = (MyBackground) paramMap.get("selectedBackground");
    }
}
