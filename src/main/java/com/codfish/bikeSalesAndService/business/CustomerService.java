package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.CustomerDAO;
import com.codfish.bikeSalesAndService.domain.Customer;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    public void issueInvoice(Customer customer) {
        customerDAO.issuerInvoice(customer);
    }

    @Transactional
    public Customer findCustomer(String email) {
        Optional<Customer> customer = customerDAO.findByEmail(email);
        if (customer.isEmpty()) {
            throw new NotFoundException("Could not find customer by email: [%s]".formatted(email));
        }
        return customer.get();
    }

    @Transactional
    public void saveServiceRequest(Customer customer) {
        customerDAO.saveServiceRequest(customer);
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }

    @Transactional
    public void deleteCustomer(String customerEmail) {
        Optional<Customer> customer = customerDAO.findByEmail(customerEmail);
        if (customer.isPresent()) {
            customerDAO.deleteCustomer(customer.get());
        } else {
            throw new NotFoundException("Customer with email: " + customerEmail + " not found.");
        }
    }
}
