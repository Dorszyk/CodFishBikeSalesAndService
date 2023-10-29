package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.Invoice;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceEntityMapper {

    InvoiceEntity mapToEntity(Invoice invoice);
}
