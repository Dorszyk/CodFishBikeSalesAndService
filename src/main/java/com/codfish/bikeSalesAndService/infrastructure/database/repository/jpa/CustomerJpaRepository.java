package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;


import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {
    boolean existsByEmail(String email);
    Optional<CustomerEntity> findByEmail(String email);
}

