package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BikeToBuyJpaRepository extends JpaRepository<BikeToBuyEntity, Integer> {
    @Query("""
            SELECT bike FROM BikeToBuyEntity bike
            LEFT JOIN FETCH bike.invoice invoice 
            WHERE invoice.bike.bikeToBuyId IS NULL 
            """)
    List<BikeToBuyEntity> findAvailableBike();

    Optional<BikeToBuyEntity> findBySerial(String serial);
}
