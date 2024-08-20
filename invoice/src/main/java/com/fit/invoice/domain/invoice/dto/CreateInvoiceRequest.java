package com.fit.invoice.domain.invoice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvoiceRequest {

    // 기본 정보
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate; // 인보이스 발행 날짜
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;     // 결제 기한

    // 거래 당사자 정보
    private String senderName;     // 발행자 이름
    private String senderAddress;  // 발행자 주소
    private String senderContact;  // 발행자 연락처
    private String recipientName;  // 수신자 이름
    private String recipientAddress; // 수신자 주소
    private String recipientContact; // 수신자 연락처

    // 상품 및 서비스 정보
    private List<InvoiceItem> items; // 상품 또는 서비스 목록

    // 가격 및 세금 정보
    private BigDecimal subTotal;    // 총 가격 (세금 제외)
    private BigDecimal taxRate;     // 세율
    private BigDecimal taxAmount;   // 세금 금액
    private BigDecimal discount;    // 할인 금액
    private BigDecimal totalAmount; // 최종 금액

    // 결제 정보
    private String paymentTerms;    // 결제 조건
    private String paymentMethod;   // 결제 방법
    private String bankDetails;     // 은행 계좌 정보 (필요시)
    private String paymentStatus;   // 결제 상태

    // 추가 정보
    private String notes;           // 메모
    private String termsAndConditions; // 거래 조건 및 규정
    private String currency;        // 통화

    // 참조 정보
    private String referenceNumber; // 참조 번호

    // InvoiceItem 클래스 정의 (내부 클래스 또는 별도 파일로 관리 가능)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceItem {
        private String itemName;         // 상품 또는 서비스 이름
        private String itemDescription;  // 상품 또는 서비스 설명
        private int quantity;            // 수량
        private BigDecimal unitPrice;    // 단가
        private BigDecimal totalPrice;   // 총 가격 (수량 * 단가)
    }
}