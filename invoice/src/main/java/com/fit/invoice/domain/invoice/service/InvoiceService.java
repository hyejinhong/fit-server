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

    public String insertInvoice(CreateInvoiceRequest request) {
        String uuid = UUID.randomUUID().toString();

        // CreateInvoiceRequest to Invoice
        Invoice invoice = Invoice.builder()
                .id(uuid)
                .invoiceDate(request.getInvoiceDate())
                .dueDate(request.getDueDate())
                .senderName(request.getSenderName())
                .senderAddress(request.getSenderAddress())
                .senderContact(request.getSenderContact())
                .senderEmail(request.getSenderEmail())
                .recipientName(request.getRecipientName())
                .recipientAddress(request.getRecipientAddress())
                .recipientContact(request.getRecipientContact())
                .recipientEmail(request.getRecipientEmail())
                .member(SecurityUtil.getCurrentMember().getMember())
                .items(new ArrayList<>())
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

        // Dto To Entity
        List<Item> items = request.getItems().stream()
                .map(invoiceItem -> {
                    return Item.builder()
                            .itemName(invoiceItem.getItemName())
                            .itemDescription(invoiceItem.getItemDescription())
                            .quantity(invoiceItem.getQuantity())
                            .unitPrice(invoiceItem.getUnitPrice())
                            .totalPrice(invoiceItem.getTotalPrice())
                            .invoice(invoice)
                            .build();
                }).toList();

        invoice.getItems().addAll(items);
        invoiceRepository.save(invoice);
        return uuid;
    }

    public GetInvoiceResponse selectInvoice(String invoiceId) {
        Optional<Invoice> optional = invoiceRepository.findById(invoiceId);

        if (optional.isEmpty())
            throw new InvoiceException(InvoiceExceptionType.NOT_FOUND);

        Invoice invoice = optional.get();

        List<ItemDto> items = invoice.getItems().stream()
                .map(invoiceItem -> {
                    return ItemDto.builder()
                            .id(invoiceItem.getId())
                            .itemName(invoiceItem.getItemName())
                            .itemDescription(invoiceItem.getItemDescription())
                            .quantity(invoiceItem.getQuantity())
                            .unitPrice(invoiceItem.getUnitPrice())
                            .totalPrice(invoiceItem.getTotalPrice())
                            .invoiceId(invoiceItem.getInvoice().getId())
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
                .senderEmail(invoice.getSenderEmail())
                .recipientName(invoice.getRecipientName())
                .recipientAddress(invoice.getRecipientAddress())
                .recipientContact(invoice.getRecipientContact())
                .recipientEmail(invoice.getRecipientEmail())
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
                .termsAndConditions(invoice.getTermsAndConditions())
                .notes(invoice.getNotes())
                .currency(invoice.getCurrency())
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
                                        .id(invoiceItem.getId())
                                        .itemName(invoiceItem.getItemName())
                                        .itemDescription(invoiceItem.getItemDescription())
                                        .quantity(invoiceItem.getQuantity())
                                        .unitPrice(invoiceItem.getUnitPrice())
                                        .totalPrice(invoiceItem.getTotalPrice())
                                        .invoiceId(invoiceItem.getInvoice().getId())
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
                            .senderEmail(invoice.getSenderEmail())
                            .recipientName(invoice.getRecipientName())
                            .recipientAddress(invoice.getRecipientAddress())
                            .recipientContact(invoice.getRecipientContact())
                            .recipientEmail(invoice.getRecipientEmail())
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
                            .termsAndConditions(invoice.getTermsAndConditions())
                            .notes(invoice.getNotes())
                            .currency(invoice.getCurrency())
                            .build();
                }).toList();

        return GetInvoiceListResponse.builder()
                .invoices(invoices)
                .totalItems(invoices.size())
                .build();
    }

    /**
     * 수신인 명으로 인보이스 리스트 검색
     * @param recipient
     * @return
     */
    public GetInvoiceListResponse selectInvoicesByRecipient(String recipient) {
        List<GetInvoiceResponse> invoices = invoiceRepository.findByRecipientName(recipient)
                .stream().map(invoice -> {
                    List<ItemDto> items = invoice.getItems().stream()
                            .map(invoiceItem -> {
                                return ItemDto.builder()
                                        .id(invoiceItem.getId())
                                        .itemName(invoiceItem.getItemName())
                                        .itemDescription(invoiceItem.getItemDescription())
                                        .quantity(invoiceItem.getQuantity())
                                        .unitPrice(invoiceItem.getUnitPrice())
                                        .totalPrice(invoiceItem.getTotalPrice())
                                        .invoiceId(invoiceItem.getInvoice().getId())
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
                            .senderEmail(invoice.getSenderEmail())
                            .recipientName(invoice.getRecipientName())
                            .recipientAddress(invoice.getRecipientAddress())
                            .recipientContact(invoice.getRecipientContact())
                            .recipientEmail(invoice.getRecipientEmail())
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
                            .termsAndConditions(invoice.getTermsAndConditions())
                            .notes(invoice.getNotes())
                            .currency(invoice.getCurrency())
                            .build();
                }).toList();

        return GetInvoiceListResponse.builder()
                .invoices(invoices)
                .totalItems(invoices.size())
                .build();
    }

    public void delete(String invoiceId) {
        invoiceRepository.delete(invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceException(InvoiceExceptionType.NOT_FOUND)));
    }
}
