package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.CustomerDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.CustomerMapper;
import com.codfish.bikeSalesAndService.business.CustomerService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.AddressEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.CustomerRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final String PURCHASE_CUSTOMER = "/customers-purchases";
    private final String ADD_CUSTOMER = "/add_customer";
    private static final String DELETE_CUSTOMER = "/delete_customer";

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerService customerService;

    @GetMapping(value = PURCHASE_CUSTOMER)
    public ModelAndView bikeCustomerPage() {
        Map<String, ?> model = prepareBikeCustomerData();

        return new ModelAndView("info/customers_purchases", model);
    }

    private Map<String, ?> prepareBikeCustomerData() {
        var availableCustomers = customerRepository.findAvailable().stream()
                .map(customerMapper::map)
                .toList();
        return Map.of(
                "availableCustomerDTOs", availableCustomers,
                "customerDTOs", CustomerDTO.buildDefault()
        );
    }

    @PostMapping(value = ADD_CUSTOMER)
    public String addCustomer(
            @ModelAttribute("availableCustomerDTOs") CustomerDTO customerDTO, Model model
    ) {
        Optional<CustomerEntity> existingCustomer = customerJpaRepository.findByEmail(customerDTO.getEmail());
        if (existingCustomer.isPresent()) {
            throw new ProcessingException(
                    "Customer with email: [%s] already exist in the database.".formatted(customerDTO.getEmail()));
        }
        CustomerEntity newCustomer = CustomerEntity.builder()
                .name(customerDTO.getName())
                .surname(customerDTO.getSurname())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .address(AddressEntity.builder()
                        .country(customerDTO.getCountry())
                        .city(customerDTO.getCity())
                        .postalCode(customerDTO.getPostalCode())
                        .address(customerDTO.getAddress())
                        .houseNumber(customerDTO.getHouseNumber())
                        .apartmentNumber(customerDTO.getApartmentNumber())
                        .build())
                .build();
        customerJpaRepository.save(newCustomer);

        var availableCustomers = customerRepository.findAvailable().stream()
                .map(customerMapper::map)
                .toList();
        model.addAttribute("availableCustomerDTOs", availableCustomers);
        model.addAttribute("currentPage", "add_customer");

        return "info/add_customer_done";

    }

    @DeleteMapping(value = DELETE_CUSTOMER)
    public String deleteCustomer(
            @RequestParam("email") String customerEmail, Model model
    ) {
        customerService.deleteCustomer(customerEmail);
        var availableCustomers = customerRepository.findAvailable().stream()
                .map(customerMapper::map)
                .toList();
        model.addAttribute("availableCustomerDTOs", availableCustomers);
        return "info/delete_customer_done";
    }
}
