package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.BikeServiceCustomerRequestDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeServicePersonProcessingUnitDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeServiceRequestDTO;
import com.codfish.bikeSalesAndService.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BikeServiceRequestMapper extends OffsetDateTimeMapper {

    default BikeServiceRequest map(BikeServiceCustomerRequestDTO dto) {
        if (dto.isNewBikeCandidate()) {
            return BikeServiceRequest.builder()
                    .customer(Customer.builder()
                            .name(dto.getCustomerName())
                            .surname(dto.getCustomerSurname())
                            .phone(dto.getCustomerPhone())
                            .email(dto.getCustomerEmail())
                            .address(Address.builder()
                                    .country(dto.getCustomerAddressCountry())
                                    .city(dto.getCustomerAddressCity())
                                    .postalCode(dto.getCustomerAddressPostalCode())
                                    .address(dto.getCustomerAddressStreet())
                                    .address(dto.getCustomerAddressApartmentNumber())
                                    .address(dto.getCustomerAddressHouseNumber())
                                    .build())
                            .build())
                    .bike(BikeToService.builder()
                            .serial(dto.getBikeSerial())
                            .brand(dto.getBikeBrand())
                            .model(dto.getBikeModel())
                            .year(dto.getBikeYear())
                            .build())
                    .customerComment(dto.getCustomerComment())
                    .build();
        } else {
            return BikeServiceRequest.builder()
                    .customer(Customer.builder()
                            .email(dto.getExistingCustomerEmail())
                            .build())
                    .bike(BikeToService.builder()
                            .serial(dto.getExistingBikeSerial())
                            .build())
                    .build();
        }
    }

    @Mapping(source = "bike.serial", target = "bikeSerial")
    @Mapping(source = "receivedDateTime", target = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(source = "completedDateTime", target = "completedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    BikeServiceRequestDTO map(BikeServiceRequest request);

    @Mapping(source = "personRepairingComment", target = "comment")
    BikeServiceProcessingRequest map(BikeServicePersonProcessingUnitDTO dto);
}
