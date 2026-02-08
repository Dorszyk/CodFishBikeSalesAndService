package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.ServiceRequestProcessingDAO;
import com.codfish.bikeSalesAndService.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@AllArgsConstructor
@org.springframework.stereotype.Service
public class BikeServiceProcessingService {

    private final PersonRepairingService personRepairingService;
    private final BikeService bikeService;
    private final ServiceCatalogService serviceCatalogService;
    private final PartCatalogService partCatalogService;
    private final BikeServiceRequestService bikeServiceRequestService;
    private final CustomerService customerService;
    private final ServiceRequestProcessingDAO serviceRequestProcessingDAO;

    private final ConcurrentHashMap<String, Integer> dailySequenceNumbers = new ConcurrentHashMap<>();

    @Transactional
    public void process(BikeServiceProcessingRequest request) {
        PersonRepairing personRepairing = personRepairingService.findPersonRepairing(request.getPersonRepairingCodeNameSurname());
        bikeService.findBikeToService(request.getBikeSerial()).orElseThrow();
        BikeServiceRequest serviceRequest = bikeServiceRequestService.findAnyActiveServiceRequest(request.getBikeSerial());

        if (request.getServices() == null || request.getServices().isEmpty()) {
            throw new RuntimeException("Nie można przetworzyć zlecenia bez wykonanych usług.");
        }

        boolean isDone = request.getDone() != null && request.getDone();
        if (isDone) {
            serviceRequest = serviceRequest.withCompletedDateTime(OffsetDateTime.now());
        }

        final BikeServiceRequest finalServiceRequest = serviceRequest;

        List<ServicePerson> servicePeople = request.getServices().stream()
                .map(entry -> {
                    Service service = serviceCatalogService.findService(entry.getServiceCode());
                    return buildServicePerson(entry, personRepairing, finalServiceRequest, service, request.getComment());
                })
                .collect(Collectors.toList());

        if (request.partsIncluded()) {
            List<ServicePart> serviceParts = request.getParts().stream()
                    .filter(entry -> !Part.NONE.equals(entry.getPartSerialNumber()))
                    .map(entry -> {
                        Part part = partCatalogService.findPart(entry.getPartSerialNumber());
                        return ServicePart.builder()
                                .quantity(entry.getPartQuantity())
                                .bikeServiceRequest(finalServiceRequest)
                                .part(part)
                                .build();
                    })
                    .collect(Collectors.toList());

            if (serviceParts.isEmpty()) {
                serviceRequestProcessingDAO.process(serviceRequest, servicePeople);
            } else {
                serviceRequestProcessingDAO.process(serviceRequest, servicePeople, serviceParts);
            }
        } else {
            serviceRequestProcessingDAO.process(serviceRequest, servicePeople);
        }

        if (isDone) {
            issueServiceInvoice(serviceRequest);
        }
    }

    private void issueServiceInvoice(BikeServiceRequest serviceRequest) {
        if (serviceRequest.getCustomer() == null) {
            throw new RuntimeException("Could not issue service invoice: Customer information missing from service request: " + serviceRequest.getBikeServiceRequestNumber());
        }
        Customer customer = customerService.findCustomer(serviceRequest.getCustomer().getEmail());
        Invoice invoice = buildInvoice(serviceRequest, customer);
        
        Set<Invoice> invoices = customer.getInvoices();
        invoices.add(invoice);
        customerService.issueInvoice(customer.withInvoices(invoices));
    }

    private Invoice buildInvoice(BikeServiceRequest serviceRequest, Customer customer) {
        OffsetDateTime now = OffsetDateTime.now();
        return Invoice.builder()
                .invoiceNumber(generateInvoiceNumber(now))
                .dateTime(now)
                .customer(customer)
                .bikeServiceRequest(serviceRequest)
                .build();
    }

    public String generateInvoiceNumber(OffsetDateTime when) {
        String dateKey = when.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String timeKey = when.toLocalTime().format(DateTimeFormatter.ofPattern("HHmmss"));

        Integer sequenceNumber = dailySequenceNumbers.compute(dateKey, (key, currentValue) -> {
            if (currentValue == null) {
                return 1;
            } else {
                return currentValue + 1;
            }
        });

        return String.format("SRV-%s/%s/%02d", dateKey, timeKey, sequenceNumber);
    }

    private ServicePerson buildServicePerson(
            BikeServiceProcessingRequest.ServiceEntry entry,
            PersonRepairing personRepairing,
            BikeServiceRequest serviceRequest,
            Service service,
            String comment
    ) {
        return ServicePerson.builder()
                .hours(entry.getHours())
                .comment(comment != null ? comment : "No comment")
                .bikeServiceRequest(serviceRequest)
                .personRepairing(personRepairing)
                .service(service)
                .build();
    }
}
