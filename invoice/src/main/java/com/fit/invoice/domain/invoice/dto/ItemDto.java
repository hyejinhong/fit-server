package com.fit.invoice.domain.invoice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
public class ItemDto {
    private Long id;

    private String itemName;         // 상품 또는 서비스 이름
    private String itemDescription;  // 상품 또는 서비스 설명
    private int quantity;            // 수량
    private BigDecimal unitPrice;    // 단가
    private BigDecimal totalPrice;   // 총 가격 (수량 * 단가)

    private String invoiceId;
}
