package com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa;

import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepairingJpaRepository extends JpaRepository<PersonRepairingEntity, Integer> {
    Optional<PersonRepairingEntity> findByCodeNameSurname(String codeNameSurname);

}
