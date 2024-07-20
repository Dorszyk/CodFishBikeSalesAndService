package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.CustomerDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.CustomerMapper;
import com.codfish.bikeSalesAndService.business.CustomerService;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.AddressEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.CustomerRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.CustomerJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private static final String PURCHASE_CUSTOMER = "/customers_purchases";
    private static final String ADD_CUSTOMER = "/add_customer";
    private static final String UPDATE_CUSTOMER = "/update_customer";
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
        var availableCustomers = mapAvailableCustomers();
        var defaultCustomer = getDefaultCustomer();
        return buildCustomerDataMap(availableCustomers, defaultCustomer);
    }

    private List<CustomerDTO> mapAvailableCustomers() {
        return fetchAndMapCustomers();
    }

    private List<CustomerDTO> fetchAndMapCustomers() {
        return customerRepository.findAvailable()
                .stream()
                .map(customerMapper::map)
                .toList();
    }

    private CustomerDTO getDefaultCustomer() {
        return CustomerDTO.buildDefault();
    }

    private Map<String, ?> buildCustomerDataMap(
            List<CustomerDTO> availableCustomers,
            CustomerDTO defaultCustomer) {
        String availableCustomersKey = "availableCustomerDTOs";
        String defaultCustomersKey = "customerDTOs";
        return Map.of(
                availableCustomersKey, availableCustomers,
                defaultCustomersKey, defaultCustomer
        );
    }

    @PostMapping(value = ADD_CUSTOMER)
    public String addCustomer(
            @Valid @ModelAttribute("availableCustomerDTOs") CustomerDTO customerDTO, Model model
    ) {
        validateCustomerExistence(customerDTO.getEmail());
        CustomerEntity newCustomer = mapDTOtoEntity(customerDTO);
        customerJpaRepository.save(newCustomer);
        updateModelAttributes(model);
        return "info/add_customer_done";
    }

    private void validateCustomerExistence(String email) {
        Optional<CustomerEntity> existingCustomer = customerJpaRepository.findByEmail(email);
        if (existingCustomer.isPresent()) {
            throw new ProcessingException("Customer with email exist: [%s]".formatted(email));
        }
    }

    private CustomerEntity mapDTOtoEntity(CustomerDTO customerDTO) {
        return CustomerEntity.builder()
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
    }

    private void updateModelAttributes(Model model) {
        model.addAttribute("availableCustomerDTOs", getAvailableCustomers());
        model.addAttribute("currentPage", getCurrentPage());
    }

    private List<CustomerDTO> getAvailableCustomers() {
        return customerRepository.findAvailable()
                .stream()
                .map(customerMapper::map
                ).toList();
    }

    private String getCurrentPage() {
        return "add_customer";
    }
    @RequestMapping(value = UPDATE_CUSTOMER)
    public String updateCustomer(
            @Valid @ModelAttribute("availableCustomerDTOs") CustomerDTO customerDTO,
            Model model
    ) {
        updateCustomerWithDTO(customerDTO);
        model.addAttribute("availableCustomerDTOs", getAvailableCustomers());
        return "info/update_customer_done";
    }

    private void updateCustomerWithDTO(@Valid @ModelAttribute("availableCustomerDTOs") CustomerDTO customerDTO) {
        CustomerEntity existingCustomerEntity = findExistingCustomer(customerDTO.getEmail());

        existingCustomerEntity.setName(customerDTO.getName());
        existingCustomerEntity.setSurname(customerDTO.getSurname());
        existingCustomerEntity.setEmail(customerDTO.getEmail());
        existingCustomerEntity.setPhone(customerDTO.getPhone());

        AddressEntity existingAddress = existingCustomerEntity.getAddress();

        if (existingAddress != null) {
            existingAddress.setCountry(customerDTO.getCountry());
            existingAddress.setCity(customerDTO.getCity());
            existingAddress.setPostalCode(customerDTO.getPostalCode());
            existingAddress.setAddress(customerDTO.getAddress());
            existingAddress.setHouseNumber(customerDTO.getHouseNumber());
            existingAddress.setApartmentNumber(customerDTO.getApartmentNumber());
        } else {
            existingCustomerEntity.setAddress(createAddressFromDto(customerDTO));
        }

        customerJpaRepository.save(existingCustomerEntity);
    }

    private CustomerEntity findExistingCustomer(String email) {
        return customerJpaRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingException(
                        "Customer not found, Email: [%s]".formatted(email)));
    }

    private AddressEntity createAddressFromDto(CustomerDTO customerDTO) {
        return AddressEntity.builder()
                .country(customerDTO.getCountry())
                .city(customerDTO.getCity())
                .postalCode(customerDTO.getPostalCode())
                .address(customerDTO.getAddress())
                .houseNumber(customerDTO.getHouseNumber())
                .apartmentNumber(customerDTO.getApartmentNumber())
                .build();
    }

    @DeleteMapping(value = DELETE_CUSTOMER)
    public String deleteCustomer(
            @RequestParam("email") String customerEmail, Model model
    ) {
        customerService.deleteCustomer(customerEmail);
        updateModelAttributes(model);
        return "info/delete_customer_done";
    }
}