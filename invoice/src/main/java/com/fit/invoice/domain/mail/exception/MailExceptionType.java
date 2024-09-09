package com.fit.invoice.domain.mail.exception;

import com.fit.invoice.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MailExceptionType implements BaseExceptionType {
    AUTH_CODE_NOT_FOUND("01", HttpStatus.NOT_FOUND, "유효한 인증코드가 존재하지 않습니다."),
    WRONG_AUTH_CODE("02", HttpStatus.BAD_REQUEST, "인증코드가 유효하지 않습니다.");

    private String errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

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
