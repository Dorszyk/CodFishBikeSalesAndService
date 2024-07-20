package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikePurchaseDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeToBuyDTO;
import com.codfish.bikeSalesAndService.api.dto.CustomerDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikePurchaseMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.CustomerMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.business.CustomerService;
import com.codfish.bikeSalesAndService.domain.*;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PurchaseController {
    private static final String PURCHASE_NEW_CUSTOMER = "/purchase_new_customer";
    static final String PURCHASE = "/purchase";
    private final BikePurchaseService bikePurchaseService;
    private final BikePurchaseMapper bikePurchaseMapper;
    private final BikeMapper bikeMapper;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @GetMapping(value = PURCHASE)
    public ModelAndView bikePurchasePage() {
        return prepareModelAndView("/info/bike_purchase");
    }

    @GetMapping(value = PURCHASE_NEW_CUSTOMER)
    public ModelAndView bikePurchaseNewCustomerPage() {
        return prepareModelAndView("/info/bike_purchase_new_customer");
    }

    private ModelAndView prepareModelAndView(String viewPath) {
        Map<String, ?> model = prepareBikePurchaseData();
        return new ModelAndView(viewPath, model);
    }

    private Map<String, ?> prepareBikePurchaseData() {
        return Map.of(
                "availableBikeDTOs", this.getAvailableBikes(),
                "availableBikeSerials", this.getAvailableBikeSerials(),
                "availableSalesmanCodeNameSurnames", this.getAvailableSalesmanCodeNameSurnames(),
                "availableCustomerDTOs", this.getAvailableCustomers(),
                "bikePurchaseDTO", BikePurchaseDTO.buildDefaultData()
        );
    }

    private List<BikeToBuyDTO> getAvailableBikes() {
        return bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
    }

    private List<String> getAvailableBikeSerials() {
        return this.getAvailableBikes().stream()
                .map(BikeToBuyDTO::getSerial)
                .toList();
    }

    private List<String> getAvailableSalesmanCodeNameSurnames() {
        return bikePurchaseService.availableSalesmen().stream()
                .map(Salesman::getCodeNameSurname)
                .toList();
    }

    private List<CustomerDTO> getAvailableCustomers() {
        return customerRepository.findAvailable().stream()
                .map(customerMapper::map)
                .toList();
    }

    @PostMapping(value = {PURCHASE, PURCHASE_NEW_CUSTOMER})
    public String makePurchase(@Valid @ModelAttribute("bikePurchaseDTO") BikePurchaseDTO bikePurchaseDTO, ModelMap model, HttpServletRequest request) {
        BikePurchaseRequest purchaseRequest = bikePurchaseMapper.map(bikePurchaseDTO);
        Invoice invoice = bikePurchaseService.purchase(purchaseRequest);
        buildModelAttributes(bikePurchaseDTO, model);
        model.addAttribute("invoiceNumber", invoice.getInvoiceNumber());
        return determineRedirectUri(request);
    }

    private void buildModelAttributes(BikePurchaseDTO bikePurchaseDTO, ModelMap model) {
        Optional<Customer> existingCustomer = customerService.findByEmail(bikePurchaseDTO.getExistingCustomerEmail());
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            buildModelAttributesFromCustomer(customer, model);
        } else {
            buildModelAttributesFromDTO(bikePurchaseDTO, model);
        }
        model.addAttribute("bikeSerial", bikePurchaseDTO.getBikeSerial());
        model.addAttribute("existingCustomerEmail", bikePurchaseDTO.getExistingCustomerEmail());
    }

    private void buildModelAttributesFromCustomer(Customer customer, ModelMap model) {
        model.addAttribute("customerName", customer.getName());
        model.addAttribute("customerSurname", customer.getSurname());
        model.addAttribute("customerPhone", customer.getPhone());
        Address address = customer.getAddress();
        model.addAttribute("customerAddressCity", address.getCity());
        model.addAttribute("customerAddressPostalCode", address.getPostalCode());
        model.addAttribute("customerAddressStreet", address.getAddress());
        model.addAttribute("customerAddressHouseNumber", address.getHouseNumber());
        model.addAttribute("customerAddressApartmentNumber", address.getApartmentNumber());
    }

    private void buildModelAttributesFromDTO(BikePurchaseDTO bikePurchaseDTO, ModelMap model) {
        model.addAttribute("customerName", bikePurchaseDTO.getCustomerName());
        model.addAttribute("customerSurname", bikePurchaseDTO.getCustomerSurname());
        model.addAttribute("customerPhone", bikePurchaseDTO.getCustomerPhone());
        model.addAttribute("customerAddressCity", bikePurchaseDTO.getCustomerAddressCity());
        model.addAttribute("customerAddressPostalCode", bikePurchaseDTO.getCustomerAddressPostalCode());
        model.addAttribute("customerAddressStreet", bikePurchaseDTO.getCustomerAddressStreet());
        model.addAttribute("customerAddressHouseNumber", bikePurchaseDTO.getCustomerAddressHouseNumber());
        model.addAttribute("customerAddressApartmentNumber", bikePurchaseDTO.getCustomerAddressApartmentNumber());
    }

    private String determineRedirectUri(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (requestUri.contains("/purchaseNewCustomer")) {
            return "info/bike_purchase_new_customer_done";
        }
        return "info/bike_purchase_done";
    }
}