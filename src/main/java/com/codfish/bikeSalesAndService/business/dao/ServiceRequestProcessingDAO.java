package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import com.codfish.bikeSalesAndService.domain.ServicePart;
import com.codfish.bikeSalesAndService.domain.ServicePerson;

import java.util.List;

public interface ServiceRequestProcessingDAO {

    void process (BikeServiceRequest serviceRequest,
                  List<ServicePerson> servicePeople);

    void process (BikeServiceRequest serviceRequest,
                  List<ServicePerson> servicePeople,
                  List<ServicePart> servicePart);
}
