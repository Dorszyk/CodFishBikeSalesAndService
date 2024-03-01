package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    Optional<Customer> findByEmail(String email);

    List<Customer> findAvailable();

    void issuerInvoice(Customer customer);

    void saveServiceRequest(Customer customer);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    boolean existsByEmail(String customerEmail);
}
