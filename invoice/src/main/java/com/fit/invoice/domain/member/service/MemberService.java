package com.fit.invoice.domain.member.service;

import com.fit.invoice.domain.member.dto.SignupDto;
import com.fit.invoice.domain.member.entity.Member;
import com.fit.invoice.domain.member.exception.MemberException;
import com.fit.invoice.domain.member.exception.MemberExceptionType;
import com.fit.invoice.domain.member.model.Authority;
import com.fit.invoice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     * */
    public void signup(SignupDto request) {
        // 중복 검증
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberException(MemberExceptionType.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .authority(Authority.ROLE_USER)
                .build();

        memberRepository.save(member);
    }
}
