package com.fit.invoice.domain.member.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
public class LoginRequest {
    private String email;
    private String password;
}
