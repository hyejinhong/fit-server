package com.fit.invoice.domain.mail.controller;

import com.fit.invoice.domain.mail.dto.VerifyAuthCodeRequest;
import com.fit.invoice.domain.mail.service.EmailService;
import com.fit.invoice.domain.member.dto.TokenResponse;
import com.fit.invoice.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    @Value("${master-key}")
    private String masterKey;

    @PostMapping("/verify")
    public BaseResponse<TokenResponse> verifyAuthCode(@RequestBody VerifyAuthCodeRequest request) {
        log.info("### 인증코드 검증 요청 : {}", request.toString());

        // TODO DEMO용 임시 PASS 처리
        if (masterKey.equals(request.getAuthCode())) {
            return new BaseResponse<>("00", "인증 성공", emailService.verifyAuthCodeForDemo(request));
        }
        return new BaseResponse<>("00", "인증 성공", emailService.verifyAuthCode(request));
    }
}
