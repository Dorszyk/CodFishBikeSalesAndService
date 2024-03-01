package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.CustomerDAO;
import com.codfish.bikeSalesAndService.domain.Customer;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.BikeServiceRequestJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.CustomerJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.InvoiceJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.BikeServiceRequestEntityMapper;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.CustomerEntityMapper;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.InvoiceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final InvoiceJpaRepository invoiceJpaRepository;
    private final BikeServiceRequestJpaRepository bikeServiceRequestJpaRepository;

    private final CustomerEntityMapper customerEntityMapper;
    private final InvoiceEntityMapper invoiceEntityMapper;
    private final BikeServiceRequestEntityMapper bikeServiceRequestEntityMapper;


    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerJpaRepository.findByEmail(email)
                .map(customerEntityMapper::mapFromEntity);
    }

    @Override
    public List<Customer> findAvailable() {
      return customerJpaRepository.findAvailable().stream()
              .map(customerEntityMapper::mapFromEntity)
              .toList();
    }

    @Override
    public void issuerInvoice(Customer customer) {
        CustomerEntity customerToSave = customerEntityMapper.mapToEntity(customer);
        CustomerEntity customerSaved = customerJpaRepository.saveAndFlush(customerToSave);

        customer.getInvoices().stream()
                .filter(invoice -> Objects.isNull(invoice.getInvoiceId()))
                .map(invoiceEntityMapper::mapToEntity)
                .forEach(invoiceEntity -> {
                    invoiceEntity.setCustomer(customerSaved);
                    invoiceJpaRepository.saveAndFlush(invoiceEntity);
                });
    }

    @Override
    public void saveServiceRequest(Customer customer) {
        CustomerEntity customerEntity = customerEntityMapper.mapToEntity(customer);

        List<BikeServiceRequestEntity> serviceRequests = customer.getBikeServiceRequests().stream()
                .filter(serviceRequest -> Objects.isNull(serviceRequest.getBikeServiceRequestId()))
                .map(bikeServiceRequestEntityMapper::mapToEntity)
                .peek(request -> request.setCustomer(customerEntity))
                .collect(Collectors.toList());
        bikeServiceRequestJpaRepository.saveAllAndFlush(serviceRequests);
    }
    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity toSave = customerEntityMapper.mapToEntity(customer);
        CustomerEntity saved = customerJpaRepository.save(toSave);
        return customerEntityMapper.mapFromEntity(saved);
    }
    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        CustomerEntity toDelete = customerEntityMapper.mapToEntity(customer);
        customerJpaRepository.delete(toDelete);
    }
}
