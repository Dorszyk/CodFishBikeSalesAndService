package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.Part;

import java.util.List;
import java.util.Optional;

public interface PartDAO {
    Optional<Part> finBySerialNumber(String serialNumber);
    List<Part> findAll();


}
