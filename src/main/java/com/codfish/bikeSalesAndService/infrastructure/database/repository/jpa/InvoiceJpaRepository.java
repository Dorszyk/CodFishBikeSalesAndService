package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<InvoiceEntity, Integer> {

    Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber);

    List<InvoiceEntity> findAllByBikeIsNotNull();

    @Query("""
            SELECT inv FROM InvoiceEntity inv
            WHERE inv.bikeServiceRequest.bikeServiceRequestNumber = :requestNumber
            """)
    Optional<InvoiceEntity> findByBikeServiceRequestNumber(@Param("requestNumber") String requestNumber);

}
