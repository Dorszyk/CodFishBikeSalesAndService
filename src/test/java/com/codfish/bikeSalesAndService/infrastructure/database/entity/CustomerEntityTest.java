package com.codfish.bikeSalesAndService.infrastructure.database.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;


class CustomerEntityTest {
    @Test
    void testEntitySettersAndGetters() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(1);
        customer.setName("Jan");
        customer.setSurname("Kowalski");
        customer.setPhone("+48 123 456 789");
        customer.setEmail("jan.kowalski@example.com");

        AddressEntity address = new AddressEntity();
        customer.setAddress(address);

        InvoiceEntity invoice = new InvoiceEntity();
        Set<InvoiceEntity> invoices = new HashSet<>();
        invoices.add(invoice);
        customer.setInvoices(invoices);

        BikeServiceRequestEntity serviceRequest = new BikeServiceRequestEntity();
        Set<BikeServiceRequestEntity> serviceRequests = new HashSet<>();
        serviceRequests.add(serviceRequest);
        customer.setBikeServiceRequests(serviceRequests);

        assertEquals(1, customer.getCustomerId());
        assertEquals("Jan", customer.getName());
        assertEquals("Kowalski", customer.getSurname());
        assertEquals("+48 123 456 789", customer.getPhone());
        assertEquals("jan.kowalski@example.com", customer.getEmail());
        assertEquals(address, customer.getAddress());
        assertEquals(invoices, customer.getInvoices());
        assertEquals(serviceRequests, customer.getBikeServiceRequests());
    }

    @Test
    void testEqualsAndHashCode() {
        CustomerEntity customer1 = new CustomerEntity(1, "Jan", "Kowalski", "+48 123 456 789", "jan.kowalski@example.com", null, null, null);
        CustomerEntity customer2 = new CustomerEntity(1, "Jan", "Kowalski", "+48 123 456 789", "jan.kowalski@example.com", null, null, null);

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void testToString() {
        CustomerEntity customer = new CustomerEntity(1, "Jan", "Kowalski", "+48 123 456 789", "jan.kowalski@example.com", null, null, null);
        String expectedString = "Jan Kowalski - jan.kowalski@example.com";
        assertEquals(expectedString, customer.toString());
    }

    @Test
    void testBuilder() {
        CustomerEntity customer = CustomerEntity.builder()
                .customerId(1)
                .name("Jan")
                .surname("Kowalski")
                .phone("+48 123 456 789")
                .email("jan.kowalski@example.com")
                .build();

        assertNotNull(customer);
        assertEquals("Jan", customer.getName());
        assertEquals("Kowalski", customer.getSurname());
        assertEquals("+48 123 456 789", customer.getPhone());
        assertEquals("jan.kowalski@example.com", customer.getEmail());
    }
}