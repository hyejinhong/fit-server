package com.fit.invoice.domain.invoice.controller;

import com.fit.invoice.domain.invoice.dto.CreateInvoiceRequest;
import com.fit.invoice.domain.invoice.dto.GetInvoiceListResponse;
import com.fit.invoice.domain.invoice.dto.GetInvoiceResponse;
import com.fit.invoice.domain.invoice.service.InvoiceService;
import com.fit.invoice.domain.member.dto.CustomUserDetails;
import com.fit.invoice.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Invoice", description = "Invoice API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public BaseResponse<Void> createInvoice(@RequestBody CreateInvoiceRequest request) {
      log.info("### 인보이스 생성 요청 : {}", request.toString());
      invoiceService.insertInvoice(request);
      return BaseResponse.ok("인보이스를 생성했습니다.", null);
    }

    @GetMapping("/{invoiceId}")
    public BaseResponse<GetInvoiceResponse> getInvoice(@PathVariable String invoiceId) {
        log.info("### 인보이스 조회 요청 : {}", invoiceId);
        return BaseResponse.ok("인보이스 조회 완료", invoiceService.selectInvoice(invoiceId));
    }

    @GetMapping("/my")
    public BaseResponse<GetInvoiceListResponse> getMyInvoices(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return BaseResponse.ok("인보이스 리스트 조회 완료", invoiceService.selectInvoiceList(customUserDetails.getMember()));
    }

    @GetMapping
    public BaseResponse<GetInvoiceListResponse> getInvoiceByRecipient(@RequestParam(required = true) String recipient) {
        return BaseResponse.ok("수신인으로 조회 완료", invoiceService.selectInvoicesByRecipient(recipient));
    }
}
