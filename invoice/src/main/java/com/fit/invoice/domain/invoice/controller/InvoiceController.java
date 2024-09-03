package com.fit.invoice.domain.invoice.controller;

import com.fit.invoice.domain.invoice.dto.CreateInvoiceRequest;
import com.fit.invoice.domain.invoice.service.InvoiceService;
import com.fit.invoice.domain.member.dto.CustomUserDetails;
import com.fit.invoice.domain.member.entity.Member;
import com.fit.invoice.domain.member.util.SecurityUtil;
import com.fit.invoice.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
      log.info("### 인보이스 생성 요청, {}", request.toString());
      invoiceService.insertInvoice(request);
      return new BaseResponse<>("00", "인보이스를 생성했습니다.", null);
    }
}
