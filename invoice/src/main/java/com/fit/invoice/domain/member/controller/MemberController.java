package com.fit.invoice.domain.member.controller;

import com.fit.invoice.domain.member.dto.SignupDto;
import com.fit.invoice.domain.member.service.MemberService;
import com.fit.invoice.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponse<Void> signup(@RequestBody SignupDto request) {
        log.info("### 회원가입 요청 : {}", request.toString());

        memberService.signup(request);
        return new BaseResponse<>("00", "회원가입이 완료되었습니다.", null);
    }
}
