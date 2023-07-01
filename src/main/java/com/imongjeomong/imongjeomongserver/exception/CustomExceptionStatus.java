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
    TOKEN_DOES_NOT_EXISTS(-1000, "토큰이 존재하지 않습니다."),
    MESSAGE_NOT_READABLE(-1001, "값이 올바르지 않습니다"),

    AUTHENTICATION_FAILED(-4000, "인증에 실패하였습니다."),
    AUTHENTICATION_TOKEN_VALIDATION_FAILED(-4001, "토큰 검증이 실패하였습니다."),
    AUTHENTICATION_MEMBER_IS_NULL(-4002, "회원 정보 획득에 실패하였습니다"),
    AUTHENTICATION_GET_MEMBER_ID_FAILED(-4003, "회원 ID를 가져오는데 실패하였습니다."),
    AUTHENTICATION_MEMBER_DUPLICATED(-4004, "중복된 회원이 있습니다."),
    AUTHENTICATION_NON_NULL_PROPERTY(-4005, "입력되지 않은 항목이 있습니다."),

    MONG_DOES_NOT_EXISTS(-5000, "몽이 존재하지 않습니다"),

    // Item 이 존재하지 않을 떄의 에러
    ITEM_NOT_FOUND(-6000, "아이템이 존재하지 않습니다."),
    ITEM_DUPLICATE(-6001, "이미 구매한 아이템입니다."),

    MEMBER_NOT_ENOUGH_GOLD(-6002, "현재 가지고 있는 골드가 부족합니다."),

    ATTRACTION_NOT_FOUND(-7000, "관광지가 존재하지 않습니다."),
    ATTRACTION_NOT_A_DAY_PASSED(-7001, "하루가 지나지 않았습니다."),

    /* 위의 어떤 것에도 해당하지 않는 에러 */
    COMMON_EXCEPTION(-9999, "예외가 발생하였습니다.");

    private final int code;
    private final String message;
}
