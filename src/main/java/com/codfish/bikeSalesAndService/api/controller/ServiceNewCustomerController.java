package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikeServiceCustomerRequestDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeServiceRequestMapper;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
public class ServiceNewCustomerController {

    private static final String SERVICE_NEW_CUSTOMER = "/service/new-customer";
    private static final String SERVICE_REQUEST_NEW_CUSTOMER = "/service/request-new-customer";

    private final BikeServiceRequestService bikeServiceRequestService;

    private final BikeServiceRequestMapper bikeServiceRequestMapper;

    @GetMapping(value = SERVICE_NEW_CUSTOMER)
    public ModelAndView bikeServicePage() {
        Map<String, ?> model = Map.of(
                "bikeServiceRequestDTO", BikeServiceCustomerRequestDTO.buildDefault()
        );
        return new ModelAndView("info/bike_service_request_new_customer", model);
    }

    @PostMapping(value = SERVICE_REQUEST_NEW_CUSTOMER)
    public String makeServiceRequest(
            @ModelAttribute("bikeServiceRequestDTO") BikeServiceCustomerRequestDTO bikeServiceCustomerRequestDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "/error";
        }
        BikeServiceRequest serviceRequest = bikeServiceRequestMapper.map(bikeServiceCustomerRequestDTO);
        bikeServiceRequestService.makeServiceRequest(serviceRequest);

        return "info/bike_service_request_new_customer_done";
    }
}
