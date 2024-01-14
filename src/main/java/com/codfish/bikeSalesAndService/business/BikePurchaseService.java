package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.CustomerDAO;
import com.codfish.bikeSalesAndService.domain.*;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class BikePurchaseService {

    private final BikeService bikeService;
    private final SalesmanService salesmanService;
    private final CustomerService customerService;
    private final CustomerDAO customerDAO;

    private final ConcurrentHashMap<String, Integer> dailySequenceNumbers = new ConcurrentHashMap<>();

    public List<BikeToBuy> availableBikes() {
        return bikeService.findAvailableBike();
    }

    public List<Salesman> availableSalesmen() {
        return salesmanService.findAvailable();
    }

    @Transactional
    public Invoice purchase(final BikePurchaseRequest request) {
        if (!existingCustomerEmailExists(request.getExistingCustomerEmail())) {
            boolean customerExists = customerDAO.existsByEmail(request.getCustomerEmail());
            if (customerExists) {
                throw new NotFoundException("A customer with this email already exists.");
            } else {
                return processFirstTimeToBuyCustomer(request);
            }
        } else {
            return processNextTimeToBuyCustomer(request);

        }
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

    private Invoice processNextTimeToBuyCustomer(BikePurchaseRequest request) {
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
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime oneHourBack = now.minusHours(1);
        String formattedDateTime = oneHourBack.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        OffsetDateTime dateTimeWithOffset = OffsetDateTime.parse(formattedDateTime + "Z",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"));

        return Invoice.builder()
                .invoiceNumber(generateInvoiceNumber(oneHourBack))
                .dateTime(dateTimeWithOffset)
                .bike(bike)
                .salesman(salesman)
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

        return String.format("FV-%s/%s/%02d", dateKey, timeKey,sequenceNumber);
    }

}
