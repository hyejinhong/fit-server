package com.fit.invoice.domain.invoice.exception;

import com.fit.invoice.global.exception.BaseException;
import com.fit.invoice.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceException extends BaseException {

    private InvoiceExceptionType exceptionType;

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
