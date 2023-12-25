package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.PersonRepairingDAO;
import com.codfish.bikeSalesAndService.domain.PersonRepairing;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PersonRepairingJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.security.UserEntity;
import com.codfish.bikeSalesAndService.infrastructure.security.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PersonRepairingService {

    private final PersonRepairingDAO personRepairingDAO;
    private final PersonRepairingJpaRepository personRepairingJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public List<PersonRepairing> findAvailable() {
        List<PersonRepairing> availablePersonRepairing = personRepairingDAO.findAvailable();
        log.info("Available Person Repairing: [{}]", availablePersonRepairing.size());
        return availablePersonRepairing;
    }

    @Transactional
    public PersonRepairing findPersonRepairing(String codeNameSurname) {
        Optional<PersonRepairing> personRepairing = personRepairingDAO.findByCodeNameSurname(codeNameSurname);
        if (personRepairing.isEmpty()) {
            throw new NotFoundException("Could not find person repairing by codeNameSurname: [%s].".formatted(codeNameSurname));
        }
        return personRepairing.get();
    }

    @Transactional
    public void deletePersonRepairingByCodeNameSurname(String codeNameSurname) {

        PersonRepairingEntity personRepairing = personRepairingJpaRepository.findByCodeNameSurname(codeNameSurname)
                        .orElseThrow(() -> new NotFoundException("Person Repairing not found"));
        personRepairingJpaRepository.delete(personRepairing);

        UserEntity user = userJpaRepository.findById(personRepairing.getUserId())
                        .orElseThrow(() -> new NotFoundException("User not found"));

        user.getRoles().clear();
        userJpaRepository.save(user);

        userJpaRepository.delete(user);
    }

}
