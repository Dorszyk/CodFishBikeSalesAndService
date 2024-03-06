package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikePurchaseDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeToBuyDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikePurchaseMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.CustomerMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.business.CustomerService;
import com.codfish.bikeSalesAndService.domain.BikePurchaseRequest;
import com.codfish.bikeSalesAndService.domain.Customer;
import com.codfish.bikeSalesAndService.domain.Invoice;
import com.codfish.bikeSalesAndService.domain.Salesman;
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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PurchaseController {
    private static final String PURCHASE_NEW_CUSTOMER = "/purchase-new-customer";
    static final String PURCHASE = "/purchase";
    private final BikePurchaseService bikePurchaseService;
    private final BikePurchaseMapper bikePurchaseMapper;
    private final BikeMapper bikeMapper;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @GetMapping(value = PURCHASE)
    public ModelAndView bikePurchasePage() {
        Map<String, ?> model = prepareBikePurchaseData();
        return new ModelAndView("/info/bike_purchase", model);
    }

    @GetMapping(value = PURCHASE_NEW_CUSTOMER)
    public ModelAndView bikePurchaseNewCustomerPage() {
        Map<String, ?> model = prepareBikePurchaseData();
        return new ModelAndView("/info/bike_purchase_new_customer", model);
    }

    private Map<String, ?> prepareBikePurchaseData() {
        var availableBike = bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
        var availableBikeSerials = availableBike.stream()
                .map(BikeToBuyDTO::getSerial)
                .toList();
        var availableSalesmanCodeNameSurnames = bikePurchaseService.availableSalesmen().stream()
                .map(Salesman::getCodeNameSurname)
                .toList();
        var availableCustomers = customerRepository.findAvailable().stream()
                .map(customerMapper::map)
                .toList();
        return Map.of(
                "availableBikeDTOs", availableBike,
                "availableBikeSerials", availableBikeSerials,
                "availableSalesmanCodeNameSurnames", availableSalesmanCodeNameSurnames,
                "availableCustomerDTOs", availableCustomers,
                "bikePurchaseDTO", BikePurchaseDTO.buildDefaultData()
        );
    }

    @PostMapping(value = {PURCHASE, PURCHASE_NEW_CUSTOMER})
    public String makePurchase(
            @Valid @ModelAttribute("bikePurchaseDTO") BikePurchaseDTO bikePurchaseDTO,
            ModelMap model,
            HttpServletRequest request
    ) {
        BikePurchaseRequest purchaseRequest = bikePurchaseMapper.map(bikePurchaseDTO);
        Invoice invoice = bikePurchaseService.purchase(purchaseRequest);

        Optional<Customer> existingCustomerEmailExists = customerService.findByEmail(bikePurchaseDTO.getExistingCustomerEmail());

        if (existingCustomerEmailExists(bikePurchaseDTO.getExistingCustomerEmail())) {

            Customer customer = existingCustomerEmailExists.get();
            model.addAttribute("customerName", customer.getName());
            model.addAttribute("customerSurname", customer.getSurname());
            model.addAttribute("customerPhone", customer.getPhone());
            model.addAttribute("customerAddressCity", customer.getAddress().getCity());
            model.addAttribute("customerAddressPostalCode", customer.getAddress().getPostalCode());
            model.addAttribute("customerAddressStreet", customer.getAddress().getAddress());
            model.addAttribute("customerAddressHouseNumber", customer.getAddress().getHouseNumber());
            model.addAttribute("customerAddressApartmentNumber", customer.getAddress().getApartmentNumber());
        } else {

            model.addAttribute("customerName", bikePurchaseDTO.getCustomerName());
            model.addAttribute("customerSurname", bikePurchaseDTO.getCustomerSurname());
            model.addAttribute("customerPhone", bikePurchaseDTO.getCustomerPhone());
            model.addAttribute("customerAddressCity", bikePurchaseDTO.getCustomerAddressCity());
            model.addAttribute("customerAddressPostalCode", bikePurchaseDTO.getCustomerAddressPostalCode());
            model.addAttribute("customerAddressStreet", bikePurchaseDTO.getCustomerAddressStreet());
            model.addAttribute("customerAddressHouseNumber", bikePurchaseDTO.getCustomerAddressHouseNumber());
            model.addAttribute("customerAddressApartmentNumber", bikePurchaseDTO.getCustomerAddressApartmentNumber());
        }

        model.addAttribute("bikeSerial", bikePurchaseDTO.getBikeSerial());
        model.addAttribute("existingCustomerEmail", bikePurchaseDTO.getExistingCustomerEmail());
        model.addAttribute("invoiceNumber", invoice.getInvoiceNumber());

        String requestUri = request.getRequestURI();
        return requestUri.contains("/purchaseNewCustomer") ?
                "info/bike_purchase_new_customer_done" :
                "info/bike_purchase_done";
    }
    private boolean existingCustomerEmailExists (String email){
        return Objects.nonNull(email) && !email.isBlank();
    }
}
