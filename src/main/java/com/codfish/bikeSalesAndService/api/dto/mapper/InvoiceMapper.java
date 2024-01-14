package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.InvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(source = "invoiceId", target = "invoiceId")
    @Mapping(source = "invoiceNumber", target = "invoiceNumber")
    @Mapping(source = "dateTime", target = "dateTime")
    @Mapping(source = "bike", target = "bike")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "salesman", target = "salesman")
    InvoiceDTO map(final InvoiceDTO invoice);


}
