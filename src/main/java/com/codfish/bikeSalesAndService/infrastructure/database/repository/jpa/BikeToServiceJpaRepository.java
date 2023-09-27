package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToServiceEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BikeToServiceJpaRepository extends JpaRepository<BikeToServiceEntity, Integer> {
    Optional<BikeToServiceEntity> findOptionalBySerial(String serial);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "bikeServiceRequests.servicePerson",
                    "bikeServiceRequests.servicePerson.service",
                    "bikeServiceRequests.servicePart",
                    "bikeServiceRequests.servicePart.part",
            }
    )
    BikeToServiceEntity findBySerial(String serial);
}
