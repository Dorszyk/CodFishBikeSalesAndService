package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.PersonRepairingMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.SalesmanMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SalesmanController {

    private static final String SALESMAN = "/salesman";
    private final BikePurchaseService bikePurchaseService;
    private final BikeServiceRequestService bikeServiceRequestService;
    private final BikeMapper bikeMapper;
    private final SalesmanMapper salesmanMapper;
    private final PersonRepairingMapper personRepairingMapper;

    @GetMapping(value = SALESMAN)
    public String homePage(Model model) {
        var availableBikes = bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        var availableMechanics = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();

        model.addAttribute("availableBikeDTOs", availableBikes);
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);
        model.addAttribute("availablePersonRepairingDTOs", availableMechanics);

        return "salesmen_portal";
    }
}
