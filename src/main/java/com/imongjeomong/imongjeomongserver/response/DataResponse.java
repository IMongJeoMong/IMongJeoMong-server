package com.imongjeomong.imongjeomongserver.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class DataResponse<T> extends CommonResponse {

    public DataResponse(int code, String message) {
        super(code, message);
    }

    private T data;
}
