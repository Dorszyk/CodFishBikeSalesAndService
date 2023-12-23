package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepairingJpaRepository extends JpaRepository<PersonRepairingEntity, Integer> {
    Optional<PersonRepairingEntity> findByCodeNameSurname(String codeNameSurname);

    @Transactional
    void deletePersonRepairingEntitiesByCodeNameSurname(String codeNameSurname);

    @Query("SELECT MAX (p.userId) FROM PersonRepairingEntity p")
    Optional<Integer> findMaxUserId();

}
