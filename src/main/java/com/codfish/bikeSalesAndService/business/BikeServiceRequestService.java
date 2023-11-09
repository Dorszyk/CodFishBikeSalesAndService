package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.BikeServiceRequestDAO;
import com.codfish.bikeSalesAndService.domain.*;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
public class BikeServiceRequestService {

    private final PersonRepairingService personRepairingService;
    private final BikeService bikeService;
    private final CustomerService customerService;
    private final BikeServiceRequestDAO bikeServiceRequestDAO;
    private final Random random = new Random();

    public List<PersonRepairing> availablePersonRepairing() {
        return personRepairingService.findAvailable();
    }

    public List<BikeServiceRequest> availableServiceRequest() {
        return bikeServiceRequestDAO.findAvailable();
    }

    @Transactional
    public void makeServiceRequest(BikeServiceRequest serviceRequest) {
        if (serviceRequest.getBike().shouldExistsInBikeToBuy()) {
            processServiceRequestForExistingBike(serviceRequest);
        } else {
            processServiceRequestForNewBike(serviceRequest);
        }
    }

    private void processServiceRequestForExistingBike(BikeServiceRequest request) {
        validateSingleActiveRequest(request.getBike().getSerial());

        BikeToService bike = bikeService.findBikeToService(request.getBike().getSerial())
                .orElse(findInBikeToBuyAndSaveInBikeToService(request.getBike()));
        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());

        BikeServiceRequest bikeServiceRequest = buildBikeServiceRequest(request, bike, customer);
        Set<BikeServiceRequest> existingBikeServiceRequests = customer.getBikeServiceRequests();
        existingBikeServiceRequests.add(bikeServiceRequest);
        customerService.saveServiceRequest(customer.withBikeServiceRequests(existingBikeServiceRequests));

    }

    private void processServiceRequestForNewBike(BikeServiceRequest request) {
        validateSingleActiveRequest(request.getBike().getSerial());

        BikeToService bike = bikeService.saveBikeToService(request.getBike());
        Customer customer = customerService.saveCustomer(request.getCustomer());

        BikeServiceRequest bikeServiceRequest = buildBikeServiceRequest(request, bike, customer);
        Set<BikeServiceRequest> existingBikeServiceRequests = customer.getBikeServiceRequests();
        existingBikeServiceRequests.add(bikeServiceRequest);
        customerService.saveServiceRequest(customer.withBikeServiceRequests(existingBikeServiceRequests));

    }

    private void validateSingleActiveRequest(String bikeSerial) {
        Set<BikeServiceRequest> serviceRequests = bikeServiceRequestDAO.findActiveServiceRequestByBikeSerial(bikeSerial);
        if (serviceRequests.size() == 1) {
            throw new ProcessingException(
                    "There should be only one active service request at a time, bike serial: [%s]".formatted(bikeSerial)
            );
        }
    }

    private BikeToService findInBikeToBuyAndSaveInBikeToService(BikeToService bike) {
        BikeToBuy bikeToBuy = bikeService.findBikeToBuy(bike.getSerial());
        return bikeService.savedBikeToService(bikeToBuy);
    }

    private BikeServiceRequest buildBikeServiceRequest(
            BikeServiceRequest request,
            BikeToService bike,
            Customer customer
    ) {
        OffsetDateTime when = OffsetDateTime.now();
        return BikeServiceRequest.builder()
                .bikeServiceRequestNumber(generateBikeServiceRequestNumber(when))
                .receivedDateTime(when)
                .customerComment(request.getCustomerComment())
                .customer(customer)
                .bike(bike)
                .build();
    }

    private String generateBikeServiceRequestNumber(OffsetDateTime when) {
        return String.format(
                "%d%02d%02d%02d%02d%02d",
                when.getYear(),
                when.getMonthValue(),
                when.getDayOfMonth(),
                when.getHour(),
                when.getMinute(),
                random.nextInt(90) + 10
        );
    }

    @Transactional
    public BikeServiceRequest findAnyActiveServiceRequest(String bikeSerial) {
        Set<BikeServiceRequest> serviceRequests = bikeServiceRequestDAO.findActiveServiceRequestByBikeSerial(bikeSerial);
        if (serviceRequests.size() != 1) {
            throw new ProcessingException(
                    "There should be only one active service request at time, bike serial: [%s]".formatted(bikeSerial));
        }
        return serviceRequests.stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Could not find any service request, bike serial: [%s]".formatted(bikeSerial)));
    }
}
