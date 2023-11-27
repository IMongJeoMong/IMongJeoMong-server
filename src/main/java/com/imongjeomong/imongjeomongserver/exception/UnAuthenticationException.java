package com.imongjeomong.imongjeomongserver.exception;

public class UnAuthenticationException extends CommonException{
    public UnAuthenticationException(CustomExceptionStatus customExceptionStatus) {
        super(customExceptionStatus);
    }
}
