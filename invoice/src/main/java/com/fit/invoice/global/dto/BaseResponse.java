package com.fit.invoice.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private String code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> ok(String message, T data) {
        return new BaseResponse<T>("00", message, data);
    }
}