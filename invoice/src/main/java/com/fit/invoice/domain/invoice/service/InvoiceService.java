package com.fit.invoice.domain.invoice.service;

import com.fit.invoice.domain.invoice.dto.CreateInvoiceRequest;
import com.fit.invoice.domain.invoice.entity.Invoice;
import com.fit.invoice.domain.invoice.entity.Item;
import com.fit.invoice.domain.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public void insertInvoice(CreateInvoiceRequest request) {
        // InvoiceItem to Item
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
}
