package com.fit.invoice.domain.mail.exception;

import com.fit.invoice.global.exception.BaseException;
import com.fit.invoice.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailException extends BaseException {
    private BaseExceptionType exceptionType;

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
