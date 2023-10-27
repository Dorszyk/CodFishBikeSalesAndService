package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.Customer;

import java.util.Optional;

public interface CustomerDAO {

    Optional<Customer> findByEmail(String email);

    void issuerInvoice(Customer customer);

    void saveServiceRequest(Customer customer);

    Customer saveCustomer(Customer customer);

}
