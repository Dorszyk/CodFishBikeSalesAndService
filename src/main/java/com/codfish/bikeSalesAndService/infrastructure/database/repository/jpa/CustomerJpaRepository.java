package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;


import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {
    boolean existsByEmail(String email);
    Optional<CustomerEntity> findByEmail(String email);

    @Query("SELECT c FROM CustomerEntity c WHERE c.invoices IS EMPTY OR c.bikeServiceRequests IS NOT EMPTY")
 //   @Query("SELECT c FROM CustomerEntity c WHERE c.invoices IS NOT EMPTY OR c.bikeServiceRequests IS NOT EMPTY")
    List<CustomerEntity> findAvailable();
}

