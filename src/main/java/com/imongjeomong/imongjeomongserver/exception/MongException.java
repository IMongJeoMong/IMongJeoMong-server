package com.imongjeomong.imongjeomongserver.exception;

public class MongException extends CommonException{
    public MongException(CustomExceptionStatus customExceptionStatus) {
        super(customExceptionStatus);
    }
}
