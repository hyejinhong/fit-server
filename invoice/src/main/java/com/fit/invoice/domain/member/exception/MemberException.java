package com.fit.invoice.domain.member.exception;

import com.fit.invoice.global.exception.BaseException;
import com.fit.invoice.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberException extends BaseException {

    private BaseExceptionType exceptionType;

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
