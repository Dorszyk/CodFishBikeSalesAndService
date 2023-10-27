package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceDAO {

    Optional<Service> findByServiceCode(String serviceCode);
    List<Service> findAll();

}
