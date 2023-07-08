package com.imongjeomong.imongjeomongserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ReviewDto {

    private Long reviewId;

    private Long memberId;
    private Long attractionId;
    private Long myAttractionId;
    private String memberName;

    private String content;

    private LocalDateTime createTime;

    private String imagePath;

}
