package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.PersonRepairing;

import java.util.List;
import java.util.Optional;

public interface PersonRepairingDAO {

    List<PersonRepairing> findAvailable();

    Optional<PersonRepairing> findByCodeNameSurname(String codeNameSurname);
}
