package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;

import java.util.List;
import java.util.Set;

public interface BikeServiceRequestDAO {

    List<BikeServiceRequest> findAvailable();

    Set<BikeServiceRequest> findActiveServiceRequestByBikeSerial(String bikeSerial);

}
