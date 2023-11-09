package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BikePurchaseService {

    private final BikeService bikeService;
    private final SalesmanService salesmanService;
    private final CustomerService customerService;

    public List<BikeToBuy> availableBikes() {
        return bikeService.findAvailableBike();
    }

    public List<Salesman> availableSalesmen() {
        return salesmanService.findAvailable();
    }

    @Transactional
    public Invoice purchase(final BikePurchaseRequest request) {
        return existingCustomerEmailExists(request.getExistingCustomerEmail())
                ? processNextTimeToBuYCustomer(request)
                : processFirstTimeToBuyCustomer(request);
    }

    private boolean existingCustomerEmailExists(String email) {
        return Objects.nonNull(email) && !email.isBlank();
    }

    private Invoice processFirstTimeToBuyCustomer(BikePurchaseRequest request) {
        BikeToBuy bike = bikeService.findBikeToBuy(request.getBikeSerial());
        Salesman salesman = salesmanService.findSalesman(request.getSalesmanCodeNameSurname());
        Invoice invoice = buildInvoice(bike, salesman);

        Customer customer = buildCustomer(request, invoice);
        customerService.issueInvoice(customer);
        return invoice;
    }

    private Invoice processNextTimeToBuYCustomer(BikePurchaseRequest request) {
        Customer existingCustomer = customerService.findCustomer(request.getExistingCustomerEmail());
        BikeToBuy bike = bikeService.findBikeToBuy(request.getBikeSerial());
        Salesman salesman = salesmanService.findSalesman(request.getSalesmanCodeNameSurname());
        Invoice invoice = buildInvoice(bike, salesman);

        Set<Invoice> existingInvoices = existingCustomer.getInvoices();
        existingInvoices.add(invoice);
        customerService.issueInvoice(existingCustomer.withInvoices(existingInvoices));
        return invoice;
    }

    private Customer buildCustomer(BikePurchaseRequest inputData, Invoice invoice) {
        return Customer.builder()
                .name(inputData.getCustomerName())
                .surname(inputData.getCustomerSurname())
                .phone(inputData.getCustomerPhone())
                .email(inputData.getCustomerEmail())
                .address(Address.builder()
                        .country(inputData.getCustomerAddressCountry())
                        .city(inputData.getCustomerAddressCity())
                        .postalCode(inputData.getCustomerAddressPostalCode())
                        .address(inputData.getCustomerAddressStreet())
                        .apartmentNumber(inputData.getCustomerAddressApartmentNumber())
                        .houseNumber(inputData.getCustomerAddressHouseNumber())
                        .build())
                .invoices(Set.of(invoice))
                .build();
    }

    private Invoice buildInvoice(BikeToBuy bike, Salesman salesman) {
        return Invoice.builder()
                .invoiceNumber(UUID.randomUUID().toString())
                .dateTime(OffsetDateTime.now())
                .bike(bike)
                .salesman(salesman)
                .build();
    }
}
