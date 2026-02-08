package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.api.dto.InvoiceDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.domain.Invoice;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.AddressEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {BikeMapper.class, BikeServiceRequestEntityMapper.class})
public interface InvoiceEntityMapper {


    @Named("mapAddressToString")
    default String mapAddressToString(AddressEntity address) {
        if (address == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append(address.getAddress()).append(" ").append(address.getHouseNumber());
        if (address.getApartmentNumber() != null && !address.getApartmentNumber().isEmpty()) {
            sb.append("/").append(address.getApartmentNumber());
        }
        sb.append(", ").append(address.getPostalCode()).append(" ").append(address.getCity());
        sb.append(", ").append(address.getCountry());
        return sb.toString();
    }
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "customer.bikeServiceRequests", ignore = true)
    @Mapping(target = "customer.invoices", ignore = true)
    @Mapping(target = "salesman.invoices", ignore = true)
    @Mapping(target = "bike.invoice", ignore = true)
    @Mapping(source = "bikeServiceRequest", target = "bikeServiceRequest")
    InvoiceEntity mapToEntity(Invoice invoice);
    @Mapping(source = "customer.address", target = "customer.address", qualifiedByName = "mapAddressToString")
    @Mapping(source = "bikeServiceRequest", target = "serviceRequest")
    InvoiceDTO mapToDto(InvoiceEntity entity);
}
