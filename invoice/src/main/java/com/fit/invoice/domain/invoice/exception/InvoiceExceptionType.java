package com.fit.invoice.domain.invoice.exception;

import com.fit.invoice.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum InvoiceExceptionType implements BaseExceptionType {
    NOT_FOUND("00", HttpStatus.NOT_FOUND, "인보이스를 찾을 수 없습니다.");

    private String errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    InvoiceExceptionType(String errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
