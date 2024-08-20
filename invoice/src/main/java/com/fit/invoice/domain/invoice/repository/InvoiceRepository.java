package com.fit.invoice.domain.invoice.repository;

import com.fit.invoice.domain.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
