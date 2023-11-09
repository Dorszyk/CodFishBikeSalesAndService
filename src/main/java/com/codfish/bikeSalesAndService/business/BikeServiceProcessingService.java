package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.ServiceRequestProcessingDAO;
import com.codfish.bikeSalesAndService.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;


@AllArgsConstructor
@org.springframework.stereotype.Service
public class BikeServiceProcessingService {

    private final PersonRepairingService personRepairingService;
    private final BikeService bikeService;
    private final ServiceCatalogService serviceCatalogService;
    private final PartCatalogService partCatalogService;
    private final BikeServiceRequestService bikeServiceRequestService;
    private final ServiceRequestProcessingDAO serviceRequestProcessingDAO;

    @Transactional
    public void process(BikeServiceProcessingRequest request) {
        PersonRepairing personRepairing = personRepairingService.findPersonRepairing(request.getPersonRepairingCodeNameSurname());
        bikeService.findBikeToService(request.getBikeSerial()).orElseThrow();
        BikeServiceRequest serviceRequest = bikeServiceRequestService.findAnyActiveServiceRequest(request.getBikeSerial());

        Service service = serviceCatalogService.findService(request.getServiceCode());

        ServicePerson servicePerson = buildServicePerson(request, personRepairing, serviceRequest, service);

        if (request.getDone()) {
            serviceRequest = serviceRequest.withCompletedDateTime(OffsetDateTime.now());
        }

        if (request.partNotIncluded()) {
            serviceRequestProcessingDAO.process(serviceRequest, servicePerson);
        } else {
            Part part = partCatalogService.findPart(request.getPartSerialNumber());
            ServicePart servicePart = buildServicePart(request, serviceRequest, part);
            serviceRequestProcessingDAO.process(serviceRequest, servicePerson, servicePart);
        }

    }

    private ServicePerson buildServicePerson(
            BikeServiceProcessingRequest request,
            PersonRepairing personRepairing,
            BikeServiceRequest serviceRequest,
            Service service
    ) {
        return ServicePerson.builder()
                .hours(request.getHours())
                .comment(request.getComment())
                .bikeServiceRequest(serviceRequest)
                .personRepairing(personRepairing)
                .service(service)
                .build();
    }

    private ServicePart buildServicePart(
            BikeServiceProcessingRequest request,
            BikeServiceRequest serviceRequest,
            Part part
    ) {
        return ServicePart.builder()
                .quantity(request.getPartQuantity())
                .bikeServiceRequest(serviceRequest)
                .part(part)
                .build();
    }
}
