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
public class ServiceController {

    private static final String SERVICE_NEW = "/service/new";
    private static final String SERVICE_REQUEST = "/service/request";

    private final BikeServiceRequestService bikeServiceRequestService;

    private final BikeServiceRequestMapper bikeServiceRequestMapper;

    @GetMapping(value = SERVICE_NEW)
    public ModelAndView bikeServicePage() {
        Map<String, ?> model = Map.of(
                "bikeServiceRequestDTO", BikeServiceCustomerRequestDTO.buildDefault()
        );
        return new ModelAndView("info/bike_service_request",model);
    }

    @PostMapping(value = SERVICE_REQUEST)
    public String makeServiceRequest(
            @ModelAttribute("bikeServiceRequestDTO") BikeServiceCustomerRequestDTO bikeServiceCustomerRequestDTO,
            BindingResult result
    ){
        if(result.hasErrors()){
            return "info/error";
        }
        BikeServiceRequest serviceRequest = bikeServiceRequestMapper.map(bikeServiceCustomerRequestDTO);
        bikeServiceRequestService.makeServiceRequest(serviceRequest);

        return "info/bike_service_request_done";
    }
}
