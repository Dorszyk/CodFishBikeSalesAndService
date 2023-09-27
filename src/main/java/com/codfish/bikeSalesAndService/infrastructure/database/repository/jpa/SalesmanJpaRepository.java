package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.SalesmanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesmanJpaRepository extends JpaRepository<SalesmanEntity, Integer> {

    Optional<SalesmanEntity>findByCodeNameSurname(String codeNameSurname);
}
