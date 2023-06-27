package com.imongjeomong.imongjeomongserver.entity.common;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
public class EditTime {

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime removeTime;

}
