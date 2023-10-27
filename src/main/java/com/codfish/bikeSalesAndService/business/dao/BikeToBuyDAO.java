package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.BikeToBuy;

import java.util.List;
import java.util.Optional;

public interface BikeToBuyDAO {

    Optional<BikeToBuy> findBikeToBuyBySerial(String bikeSerial);

    List<BikeToBuy> findAvailable();
}
