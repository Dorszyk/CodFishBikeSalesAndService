package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.BikeServiceRequestDAO;
import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.BikeServiceRequestJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.BikeServiceRequestEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BikeServiceRequestRepository implements BikeServiceRequestDAO {

    private final BikeServiceRequestJpaRepository bikeServiceRequestJpaRepository;
    private final BikeServiceRequestEntityMapper bikeServiceRequestEntityMapper;
    @Override
    public List<BikeServiceRequest> findAvailable() {
        return bikeServiceRequestJpaRepository.findAllByCompletedDateTimeIsNull().stream()
                .map(bikeServiceRequestEntityMapper::mapFromEntityWithBike)
                .toList();
    }
    @Override
    public Set<BikeServiceRequest> findActiveServiceRequestByBikeSerial(String bikeSerial) {
        return bikeServiceRequestJpaRepository.findActiveServiceRequestByBikeSerial(bikeSerial).stream()
                .map(bikeServiceRequestEntityMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }
}
