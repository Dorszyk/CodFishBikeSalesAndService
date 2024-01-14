package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.api.dto.InvoiceDTO;
import com.codfish.bikeSalesAndService.business.dao.InvoiceDAO;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.InvoiceJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.InvoiceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class InvoiceRepository implements InvoiceDAO {

    private final InvoiceJpaRepository invoiceJpaRepository;
    private final InvoiceEntityMapper invoiceEntityMapper;

    public List<InvoiceDTO> findAll() {
        List<InvoiceEntity> invoiceEntities = invoiceJpaRepository.findAll();
        return invoiceEntities.stream()
                .map(invoiceEntityMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
