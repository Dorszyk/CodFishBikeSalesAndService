package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import com.codfish.bikeSalesAndService.domain.ServicePart;
import com.codfish.bikeSalesAndService.domain.ServicePerson;

import java.util.List;

public interface ServiceRequestProcessingDAO {

    void process (BikeServiceRequest serviceRequest,
                  ServicePerson servicePerson);

    void process (BikeServiceRequest serviceRequest,
                  ServicePerson servicePerson,
                  List<ServicePart> servicePart);
}
