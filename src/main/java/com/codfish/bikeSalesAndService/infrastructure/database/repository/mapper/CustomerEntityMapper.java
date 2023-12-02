package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import com.codfish.bikeSalesAndService.domain.Customer;
import com.codfish.bikeSalesAndService.domain.Invoice;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    @Mapping(target = "address.customer", ignore = true)
    @Mapping(source = "bikeServiceRequests", target = "bikeServiceRequests", qualifiedByName = "mapBikeServiceRequests")
    @Mapping(source = "invoices", target = "invoices", qualifiedByName = "mapInvoices")
    Customer mapFromEntity(CustomerEntity entity);

    @Named("mapInvoices")
    default Set<Invoice> mapInvoices(Set<InvoiceEntity> invoiceEntities) {
        if (invoiceEntities != null) {
            return invoiceEntities.stream()
                    .map(this::mapFromEntity)
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    @Named("mapBikeServiceRequests")
    default Set<BikeServiceRequest> mapBikeServiceRequests(Set<BikeServiceRequestEntity> entities) {
        if (entities != null) {
            return entities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    @Mapping(target = "bike", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "servicePersons", ignore = true)
    @Mapping(target = "servicePart", ignore = true)
    BikeServiceRequest mapFromEntity(BikeServiceRequestEntity entity);

    @Mapping(target = "bike", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "salesman", ignore = true)
    Invoice mapFromEntity(InvoiceEntity entity);

    @Mapping(target = "bikeServiceRequests", ignore = true)
    CustomerEntity mapToEntity(Customer customer);

}