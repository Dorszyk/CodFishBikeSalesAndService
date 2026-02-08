package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.BikeHistory;
import com.codfish.bikeSalesAndService.domain.BikeToService;
import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.domain.Service;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToServiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BikeToServiceEntityMapper {

    @Mapping(target = "bikeServiceRequests", ignore = true)
    BikeToService mapFromEntity(BikeToServiceEntity entity);

    default BikeHistory mapFromEntity(String serial, BikeToServiceEntity entity, BikeServiceRequestEntityMapper bikeServiceRequestEntityMapper) {
        if (entity == null) {
            return null;
        }
        return BikeHistory.builder()
                .bikeSerial(serial)
                .bikeServiceRequests(entity.getBikeServiceRequests().stream()
                        .map(bikeServiceRequestEntityMapper::mapToBikeHistoryRequest)
                        .toList())
                .build();
    }
    BikeToServiceEntity mapToEntity(BikeToService bike);

}


