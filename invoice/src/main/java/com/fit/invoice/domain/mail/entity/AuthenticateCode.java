package com.fit.invoice.domain.mail.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "authenticationCode", timeToLive = 300)
public class AuthenticateCode {
    @Id
    private String email;
    private String authCode;
}
