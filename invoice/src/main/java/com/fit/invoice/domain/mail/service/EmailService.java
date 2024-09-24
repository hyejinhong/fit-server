package com.fit.invoice.domain.mail.service;

import com.fit.invoice.domain.mail.dto.VerifyAuthCodeRequest;
import com.fit.invoice.domain.mail.entity.AuthenticateCode;
import com.fit.invoice.domain.mail.exception.MailException;
import com.fit.invoice.domain.mail.exception.MailExceptionType;
import com.fit.invoice.domain.mail.repository.AuthCodeRedisRepository;
import com.fit.invoice.domain.member.dto.TokenResponse;
import com.fit.invoice.domain.member.util.JwtProvider;
import com.fit.invoice.domain.member.util.SecurityUtil;
import com.fit.invoice.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final AuthCodeRedisRepository authCodeRepository;
    private final JwtProvider jwtProvider;
    private final SecurityUtil securityUtil;

    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.mail.password}")
    private String password;

    public BaseResponse<String> sendMail(String email) {
        log.info("### 인증코드 이메일 전송 요청 : {}", email);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Authentication code from FIT");
        simpleMailMessage.setFrom(sender);

        String authenticateCode = generateAuthenticateCode();
        simpleMailMessage.setText("This is Your Authentication Code : " + authenticateCode);

        try {
            // 캐시서버에 저장
            AuthenticateCode authCode = AuthenticateCode
                    .builder()
                    .email(email)
                    .authCode(authenticateCode).build();
            authCodeRepository.save(authCode);

            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }

        return new BaseResponse<>("00", "메일 전송 완료", null);
    }

    public TokenResponse verifyAuthCode(VerifyAuthCodeRequest request) {
        // 캐시에서 확인
        // 캐시에 없음
        Optional<AuthenticateCode> authCodeOptional = authCodeRepository.findById(request.getEmail());
        if (authCodeOptional.isEmpty()) {
            throw new MailException(MailExceptionType.AUTH_CODE_NOT_FOUND);
        }

        AuthenticateCode authenticateCode = authCodeOptional.get();

        if (!request.getAuthCode().equals(authenticateCode.getAuthCode())) {
            throw new MailException(MailExceptionType.WRONG_AUTH_CODE);
        }

        // 토큰 발급
        Authentication authentication = SecurityUtil.getAuthentication();
        return jwtProvider.generateTokenDto(authentication);
    }

    private String generateAuthenticateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        return String.valueOf(random.nextInt(1000000));
    }
}
