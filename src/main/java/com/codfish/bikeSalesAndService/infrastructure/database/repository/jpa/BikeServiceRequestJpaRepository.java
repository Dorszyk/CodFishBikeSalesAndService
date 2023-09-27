package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BikeServiceRequestJpaRepository extends JpaRepository<BikeServiceRequestEntity, Integer> {

    @Query("""
            SELECT brs FROM BikeServiceRequestEntity brs
            WHERE brs.completedDateTime IS NULL 
            AND brs.bike.serial = :serial 
            """)
    Set<BikeServiceRequestEntity> findActiveServiceRequestByBikeSerial(final @Param("serial") String bikeSerial);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "bike"
            }
    )
    Set<BikeServiceRequestEntity> findAllByCompletedDateTimeIsNull();

}
