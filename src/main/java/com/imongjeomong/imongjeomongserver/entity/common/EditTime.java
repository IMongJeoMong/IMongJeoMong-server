package com.imongjeomong.imongjeomongserver.entity.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter @Setter
@Embeddable
@NoArgsConstructor
public class EditTime {

    @ColumnDefault("now()")
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime removeTime;

}
