package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.PersonRepairingDAO;
import com.codfish.bikeSalesAndService.domain.PersonRepairing;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PersonRepairingJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.PersonRepairingEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PersonRepairingRepository implements PersonRepairingDAO {

    private final PersonRepairingJpaRepository personRepairingJpaRepository;
    private final PersonRepairingEntityMapper personRepairingEntityMapper;

    @Override
    public List<PersonRepairing> findAvailable() {
        return personRepairingJpaRepository.findAll().stream()
                .map(personRepairingEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<PersonRepairing> findByCodeNameSurname(String codeNameSurname) {
        return personRepairingJpaRepository.findByCodeNameSurname(codeNameSurname)
                .map(personRepairingEntityMapper::mapFromEntity);
    }
}
