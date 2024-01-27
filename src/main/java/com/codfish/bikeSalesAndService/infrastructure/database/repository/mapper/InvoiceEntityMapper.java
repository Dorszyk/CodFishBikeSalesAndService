package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.api.dto.InvoiceDTO;
import com.codfish.bikeSalesAndService.domain.Invoice;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.AddressEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceEntityMapper {


    @Named("mapAddressToString")
    default String mapAddressToString(AddressEntity address) {
        return address == null ? null : address.toString();
    }
    InvoiceEntity mapToEntity(Invoice invoice);
    @Mapping(source = "customer.address", target = "customer.address", qualifiedByName = "mapAddressToString")
    InvoiceDTO mapToDto(InvoiceEntity entity);
}
