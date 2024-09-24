package com.fit.invoice.domain.invoice.service;

import com.fit.invoice.domain.invoice.dto.CreateInvoiceRequest;
import com.fit.invoice.domain.invoice.dto.GetInvoiceListResponse;
import com.fit.invoice.domain.invoice.dto.GetInvoiceResponse;
import com.fit.invoice.domain.invoice.dto.ItemDto;
import com.fit.invoice.domain.invoice.entity.Invoice;
import com.fit.invoice.domain.invoice.entity.Item;
import com.fit.invoice.domain.invoice.exception.InvoiceException;
import com.fit.invoice.domain.invoice.exception.InvoiceExceptionType;
import com.fit.invoice.domain.invoice.repository.InvoiceRepository;
import com.fit.invoice.domain.member.entity.Member;
import com.fit.invoice.domain.member.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public void insertInvoice(CreateInvoiceRequest request) {
        // Dto To Entity
        List<Item> items = request.getItems().stream()
                .map(invoiceItem -> {
                    return Item.builder()
                            .itemName(invoiceItem.getItemName())
                            .itemDescription(invoiceItem.getItemDescription())
                            .quantity(invoiceItem.getQuantity())
                            .unitPrice(invoiceItem.getUnitPrice())
                            .totalPrice(invoiceItem.getTotalPrice())
                            .build();
                }).toList();

        // CreateInvoiceRequest to Invoice
        Invoice invoice = Invoice.builder()
                .id(UUID.randomUUID().toString())
                .invoiceDate(request.getInvoiceDate())
                .dueDate(request.getDueDate())
                .senderName(request.getSenderName())
                .senderAddress(request.getSenderAddress())
                .senderContact(request.getSenderContact())
                .recipientName(request.getRecipientName())
                .recipientAddress(request.getRecipientAddress())
                .recipientContact(request.getRecipientContact())
                .member(SecurityUtil.getCurrentMember().getMember())
                .items(items)
                .subTotal(request.getSubTotal())
                .taxRate(request.getTaxRate())
                .taxAmount(request.getTaxAmount())
                .discount(request.getDiscount())
                .totalAmount(request.getTotalAmount())
                .paymentTerms(request.getPaymentTerms())
                .paymentMethod(request.getPaymentMethod())
                .bankDetails(request.getBankDetails())
                .paymentStatus(request.getPaymentStatus())
                .notes(request.getNotes())
                .termsAndConditions(request.getTermsAndConditions())
                .currency(request.getCurrency())
                .referenceNumber(request.getReferenceNumber())
                .build();

        invoiceRepository.save(invoice);
    }

    public GetInvoiceResponse selectInvoice(String invoiceId) {
        Optional<Invoice> optional = invoiceRepository.findById(invoiceId);

        if (optional.isEmpty())
            throw new InvoiceException(InvoiceExceptionType.NOT_FOUND);

        Invoice invoice = optional.get();

        List<ItemDto> items = invoice.getItems().stream()
                .map(invoiceItem -> {
                    return ItemDto.builder()
                            .itemName(invoiceItem.getItemName())
                            .itemDescription(invoiceItem.getItemDescription())
                            .quantity(invoiceItem.getQuantity())
                            .unitPrice(invoiceItem.getUnitPrice())
                            .totalPrice(invoiceItem.getTotalPrice())
                            .build();
                }).toList();

        return GetInvoiceResponse.builder()
                .id(invoice.getId())
                .invoiceDate(invoice.getInvoiceDate())
                .dueDate(invoice.getDueDate())
                .memberId(invoice.getMember().getId())
                .memberEmail(invoice.getMember().getEmail())
                .senderName(invoice.getSenderName())
                .senderAddress(invoice.getSenderAddress())
                .senderContact(invoice.getSenderContact())
                .recipientName(invoice.getRecipientName())
                .recipientAddress(invoice.getRecipientAddress())
                .recipientContact(invoice.getRecipientContact())
                .items(items)
                .subTotal(invoice.getSubTotal())
                .taxRate(invoice.getTaxRate())
                .taxAmount(invoice.getTaxAmount())
                .discount(invoice.getDiscount())
                .totalAmount(invoice.getTotalAmount())
                .paymentTerms(invoice.getPaymentTerms())
                .paymentMethod(invoice.getPaymentMethod())
                .bankDetails(invoice.getBankDetails())
                .paymentStatus(invoice.getPaymentStatus())
                .referenceNumber(invoice.getReferenceNumber())
                .build();
    }

    /**
     * Member 가 발행한 인보이스 리스트 반환
     * */
    public GetInvoiceListResponse selectInvoiceList(Member member) {
        List<GetInvoiceResponse> invoices = invoiceRepository.findByMemberId(member.getId())
                .stream().map(invoice -> {
                    List<ItemDto> items = invoice.getItems().stream()
                            .map(invoiceItem -> {
                                return ItemDto.builder()
                                        .itemName(invoiceItem.getItemName())
                                        .itemDescription(invoiceItem.getItemDescription())
                                        .quantity(invoiceItem.getQuantity())
                                        .unitPrice(invoiceItem.getUnitPrice())
                                        .totalPrice(invoiceItem.getTotalPrice())
                                        .build();
                            }).toList();

                    return GetInvoiceResponse.builder()
                            .id(invoice.getId())
                            .invoiceDate(invoice.getInvoiceDate())
                            .dueDate(invoice.getDueDate())
                            .memberId(invoice.getMember().getId())
                            .memberEmail(invoice.getMember().getEmail())
                            .senderName(invoice.getSenderName())
                            .senderAddress(invoice.getSenderAddress())
                            .senderContact(invoice.getSenderContact())
                            .recipientName(invoice.getRecipientName())
                            .recipientAddress(invoice.getRecipientAddress())
                            .recipientContact(invoice.getRecipientContact())
                            .items(items)
                            .subTotal(invoice.getSubTotal())
                            .taxRate(invoice.getTaxRate())
                            .taxAmount(invoice.getTaxAmount())
                            .discount(invoice.getDiscount())
                            .totalAmount(invoice.getTotalAmount())
                            .paymentTerms(invoice.getPaymentTerms())
                            .paymentMethod(invoice.getPaymentMethod())
                            .bankDetails(invoice.getBankDetails())
                            .paymentStatus(invoice.getPaymentStatus())
                            .referenceNumber(invoice.getReferenceNumber())
                            .build();
                }).toList();

        return GetInvoiceListResponse.builder()
                .invoices(invoices)
                .totalItems(invoices.size())
                .build();
    }
}
