package com.fit.invoice.domain.member.service;

import com.fit.invoice.domain.member.dto.MemberStatusResponse;
import com.fit.invoice.domain.member.dto.SignupRequest;
import com.fit.invoice.domain.member.entity.Member;
import com.fit.invoice.domain.member.exception.MemberException;
import com.fit.invoice.domain.member.exception.MemberExceptionType;
import com.fit.invoice.domain.member.model.Authority;
import com.fit.invoice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     * */
    public void signup(SignupRequest request) {
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

    /**
     * 회원 상태 조회
     * @return
     */
    public MemberStatusResponse getMemberStatus(String email) {
        return MemberStatusResponse.builder().isMember(memberRepository.existsByEmail(email)).build();
    }
}
