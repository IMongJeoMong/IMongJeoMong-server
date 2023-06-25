package com.imongjeomong.imongjeomongserver.exception;

import com.imongjeomong.imongjeomongserver.response.CommonResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Getter
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler(SampleException.class)
    public CommonResponse sampleExceptionHandler(SampleException e){

        CommonResponse response = new CommonResponse();
        response.setCode(e.getCustomExceptionStatus().getCode());
        response.setMessage(e.getCustomExceptionStatus().getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse exceptionHandler(Exception e) {
        CommonResponse response = new CommonResponse();
        response.setCode(9999);
        response.setMessage("예외가 발생했습니다.");
        return response;
    }

}
