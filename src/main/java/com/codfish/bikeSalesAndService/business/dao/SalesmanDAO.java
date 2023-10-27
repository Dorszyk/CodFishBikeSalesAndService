package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.Salesman;

import java.util.List;
import java.util.Optional;

public interface SalesmanDAO {

    Optional<Salesman> findByCodeNameSurname(String codeNameSurname);

    List<Salesman> findAvailable();
}
