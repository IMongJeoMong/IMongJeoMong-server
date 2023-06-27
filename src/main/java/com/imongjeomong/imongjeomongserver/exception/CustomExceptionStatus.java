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
    AUTHENTICATION_EXCEPTION(-4000, "인증에 실패하였습니다."),
    AUTHENTICATION_TOKEN_VALIDATION_FAILED(-4001, "토큰 검증이 실패하였습니다."),
    AUTHENTICATION_MEMBER_IS_NULL(-4002, "회원 정보 획득에 실패하였습니다"),
    AUTHENTICATION_GET_MEMBER_ID_FAILED(-4003, "회원 ID를 가져오는데 실패하였습니다.");

    private final int code;
    private final String message;
}
