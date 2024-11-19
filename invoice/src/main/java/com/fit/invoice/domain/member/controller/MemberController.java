package com.fit.invoice.domain.member.controller;

import com.fit.invoice.domain.member.dto.MemberStatusResponse;
import com.fit.invoice.domain.member.dto.SignupRequest;
import com.fit.invoice.domain.member.service.MemberService;
import com.fit.invoice.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponse<Void> signup(@RequestBody SignupRequest request) {
        log.info("### 회원가입 요청 : {}", request.toString());

        memberService.signup(request);
        return BaseResponse.ok("회원가입이 완료되었습니다.", null);
    }

    @GetMapping("/status")
    public BaseResponse<MemberStatusResponse> getMemberStatus(@RequestParam String email) {
        log.info("#### 회원상태 확인 요청 : {}", email);
        return BaseResponse.ok("회원상태를 조회했습니다.", memberService.getMemberStatus(email));
    }
}
