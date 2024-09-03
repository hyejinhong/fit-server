package com.fit.invoice.domain.member.util;

import com.fit.invoice.domain.member.dto.CustomUserDetails;
import com.fit.invoice.domain.member.entity.Member;
import com.fit.invoice.domain.member.exception.MemberException;
import com.fit.invoice.domain.member.exception.MemberExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    public static String getCurrentMemberEmail() {
        final Authentication authentication = getAuthentication();
        return authentication.getName();
    }

    public static CustomUserDetails getCurrentMember() {
        final Authentication authentication = getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails;
    }

    private static Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new MemberException(MemberExceptionType.UNAUTHORIZED);
        }
        return authentication;
    }
}
