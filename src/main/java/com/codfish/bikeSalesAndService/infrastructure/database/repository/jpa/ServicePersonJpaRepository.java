package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePersonJpaRepository extends JpaRepository<ServicePersonEntity, Integer> {
}
