package com.fit.invoice.global.exception;

import com.fit.invoice.global.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleBaseException(BaseException be) {
        String errorCode = be.getExceptionType().getErrorCode();
        String errorMessage = be.getExceptionType().getErrorMessage();

        log.error("BaseException Error Code : {}", errorCode);
        log.error("BaseException Error Message : {}", errorMessage);

        BaseResponse<Void> baseResponse = new BaseResponse<>(errorCode, errorMessage, null);
        return new ResponseEntity<BaseResponse<Void>>(baseResponse, be.getExceptionType().getHttpStatus());
    }
}
