package com.imongjeomong.imongjeomongserver.dto;

import com.imongjeomong.imongjeomongserver.entity.MyBackground;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private Long memberId;
    private String email;
    private String nickname;
    private LocalDate birth;
    private String gender;
    private int sidoCode;
    private int gold;

    private MyMongDto selectedMong;
    private MyItemDto selectedItem;
    private MyBackground selectedBackground;

}
