package com.codfish.bikeSalesAndService.api.dto.mapper;


import com.codfish.bikeSalesAndService.api.dto.CustomerDTO;
import com.codfish.bikeSalesAndService.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "address.country", target = "country")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.postalCode", target = "postalCode")
    @Mapping(source = "address.address", target = "address")
    @Mapping(source = "address.houseNumber", target = "houseNumber")
    @Mapping(source = "address.apartmentNumber", target = "apartmentNumber")
    CustomerDTO map(final Customer customer);
}
