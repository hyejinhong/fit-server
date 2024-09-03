package com.fit.invoice.domain.member.exception;

import com.fit.invoice.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    INTERNAL_ERROR("1", HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    DUPLICATE_EMAIL("2", HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    ;

    private String errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(String errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
