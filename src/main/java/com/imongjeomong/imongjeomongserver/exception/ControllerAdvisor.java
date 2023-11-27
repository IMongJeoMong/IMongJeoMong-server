package com.imongjeomong.imongjeomongserver.exception;

import com.imongjeomong.imongjeomongserver.response.CommonResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Getter
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler(CommonException.class)
    public CommonResponse commonExceptionHandler(CommonException e){

        CommonResponse response = new CommonResponse();
        response.setCode(e.getCustomExceptionStatus().getCode());
        response.setMessage(e.getCustomExceptionStatus().getMessage());
        return response;
    }

    @ExceptionHandler(UnAuthenticationException.class)
    public CommonResponse authenticationExceptionHandler(UnAuthenticationException e){

        CommonResponse response = new CommonResponse();
        response.setCode(e.getCustomExceptionStatus().getCode());
        response.setMessage(e.getCustomExceptionStatus().getMessage());
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e){

        CommonResponse response = new CommonResponse();
        response.setCode(CustomExceptionStatus.MESSAGE_NOT_READABLE.getCode());
        response.setMessage(CustomExceptionStatus.MESSAGE_NOT_READABLE.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse exceptionHandler(Exception e) {
        /* for Debugging */
        e.printStackTrace();

        CommonResponse response = new CommonResponse();
        response.setCode(9999);
        response.setMessage("예외가 발생했습니다.");
        return response;
    }

}
