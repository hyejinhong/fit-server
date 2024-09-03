package com.fit.invoice.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
@Builder
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private Date accessTokenExpire;
    private String refreshToken;
}
