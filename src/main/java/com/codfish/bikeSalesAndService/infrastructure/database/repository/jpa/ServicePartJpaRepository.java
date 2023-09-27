package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePartJpaRepository extends JpaRepository<ServicePartEntity, Integer> {
}
