package com.imongjeomong.imongjeomongserver.dto;

import com.imongjeomong.imongjeomongserver.entity.Attraction;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
@AllArgsConstructor
public class MyAttractionDTO {

    private Long id;
    private Long memberId;
    private Attraction attraction;

    private int count;
    private LocalDateTime visitTime;
}
