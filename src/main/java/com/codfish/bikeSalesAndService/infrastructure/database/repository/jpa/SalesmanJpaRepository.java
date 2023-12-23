package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.SalesmanEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SalesmanJpaRepository extends JpaRepository<SalesmanEntity, Integer> {

    Optional<SalesmanEntity> findByCodeNameSurname(String codeNameSurname);

    @Transactional
    void deleteSalesmanEntitiesByCodeNameSurname(String codeNameSurname);

    @Query("SELECT MAX(s.userId) FROM SalesmanEntity s")
    Optional<Integer> findMaxUserId();
}

