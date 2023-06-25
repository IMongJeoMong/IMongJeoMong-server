package com.imongjeomong.imongjeomongserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {

    /**
     * CODE : 카테고리 ( 4자리 정수 )
     * MESSAGE : 메시지
     */
    CUSTOM_EXCEPTION_SAMPLE(1000, "MESSAGE SAMPLE");

    private final int code;
    private final String message;
}
