package com.fit.invoice.domain.invoice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class GetInvoiceListResponse {
    private long totalItems;
    private List<GetInvoiceResponse> invoices;
}
