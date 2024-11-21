package com.fit.invoice.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberStatusResponse {
    private Boolean isMember;
}
