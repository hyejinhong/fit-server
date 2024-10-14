package com.fit.invoice.domain.invoice.repository;

import com.fit.invoice.domain.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByMemberId(Long memberId);
    List<Invoice> findByRecipientName(String recipientName);
}
