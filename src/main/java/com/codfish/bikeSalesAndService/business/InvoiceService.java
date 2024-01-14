package com.codfish.bikeSalesAndService.business;


import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.InvoiceJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceJpaRepository invoiceJpaRepository;

    public List<InvoiceEntity> findAll() {
        return invoiceJpaRepository.findAll();
    }
}
