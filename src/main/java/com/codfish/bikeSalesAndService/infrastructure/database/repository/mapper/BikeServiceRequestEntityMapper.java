package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;


import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import com.codfish.bikeSalesAndService.domain.BikeToService;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BikeServiceRequestEntityMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "bike", ignore = true)
    @Mapping(target = "servicePersons", ignore = true)
    @Mapping(target = "servicePart", ignore = true)
    BikeServiceRequest mapFromEntity(BikeServiceRequestEntity entity);

    default BikeServiceRequest mapFromEntityWithBike(BikeServiceRequestEntity entity) {
        return mapFromEntity(entity)
                .withBike(BikeToService.builder()
                        .serial(entity.getBike().getSerial())
                        .build());
    }

    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "customer.bikeServiceRequests", ignore = true)
    BikeServiceRequestEntity mapToEntity(BikeServiceRequest request);
}
