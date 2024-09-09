package com.fit.invoice.domain.mail.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerifyAuthCodeRequest {
    private String email;
    private String authCode;
}
