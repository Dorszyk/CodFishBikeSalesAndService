package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.BikeHistory;
import com.codfish.bikeSalesAndService.domain.BikeToBuy;
import com.codfish.bikeSalesAndService.domain.BikeToService;

import java.util.List;
import java.util.Optional;

public interface BikeToServiceDAO {

    Optional<BikeToService> findBikeToServiceBySerial(String bikeSerial);

    BikeToService saveBikeToService(BikeToService bike);

    BikeHistory findBikeHistoryBySerial(String bikeSerial);

    List<BikeToService> findAll();
}
