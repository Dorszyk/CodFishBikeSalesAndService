package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.mapper.CustomerMapper;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
@Controller
@RequiredArgsConstructor
public class CustomerController {
    public final String PURCHASE_CUSTOMER = "/customers-purchases";
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

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
                "availableCustomerDTOs", availableCustomers
        );
    }
}
