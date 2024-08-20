package com.fit.invoice.domain.invoice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;         // 상품 또는 서비스 이름
    private String itemDescription;  // 상품 또는 서비스 설명
    private int quantity;            // 수량
    private BigDecimal unitPrice;    // 단가
    private BigDecimal totalPrice;   // 총 가격 (수량 * 단가)

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
