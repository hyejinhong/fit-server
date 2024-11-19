package com.fit.invoice.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberStatusResponse {
    private Boolean isMember;
}
