package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikeServiceCustomerRequestDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeServiceRequestMapper;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
@Log4j2
public class ServiceNewCustomerController {

    private static final String SERVICE_NEW_CUSTOMER = "/service/new_customer";
    private static final String SERVICE_REQUEST_NEW_CUSTOMER = "/service/request_new_customer";

    private final BikeServiceRequestService bikeServiceRequestService;
    private final BikeServiceRequestMapper bikeServiceRequestMapper;

    @GetMapping(value = SERVICE_NEW_CUSTOMER)
    public ModelAndView newCustomerServicePage() {
        var bikeServiceRequestDTO = BikeServiceCustomerRequestDTO.buildDefault();
        var viewName = "info/bike_service_request_new_customer";
        return new ModelAndView(viewName, Map.of("bikeServiceRequestDTO", bikeServiceRequestDTO));
    }

    @PostMapping(value = SERVICE_REQUEST_NEW_CUSTOMER)
    public String makeServiceRequest(
            @Valid @ModelAttribute("bikeServiceRequestDTO") BikeServiceCustomerRequestDTO bikeServiceCustomerRequestDTO,
            BindingResult result
    ) throws ServletRequestBindingException {
        if (result.hasErrors()) {
            String errorMessage = buildErrorMessage(result);
            log.error(errorMessage);
            throw new ServletRequestBindingException(errorMessage);
        }
        BikeServiceRequest serviceRequest = bikeServiceRequestMapper.map(bikeServiceCustomerRequestDTO);
        bikeServiceRequestService.makeServiceRequest(serviceRequest);
        return "info/bike_service_request_new_customer_done";
    }

    private String buildErrorMessage(BindingResult result) {
        FieldError fieldError = result.getFieldError();
        String errorMessage = "Validation error";
        if (fieldError != null) {
            errorMessage = String.format("Validation error on field: [%s], rejected value: [%s], because: [%s]",
                    fieldError.getField(),
                    fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage());
        }
        return errorMessage;
    }
}