package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikePurchaseDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeToBuyDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikePurchaseMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.domain.BikePurchaseRequest;
import com.codfish.bikeSalesAndService.domain.Invoice;
import com.codfish.bikeSalesAndService.domain.Salesman;
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

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    static final String PURCHASE = "/purchase";
    private final BikePurchaseService bikePurchaseService;
    private final BikePurchaseMapper bikePurchaseMapper;
    private final BikeMapper bikeMapper;

    @GetMapping(value = PURCHASE)
    public ModelAndView bikePurchasePage() {
        Map<String, ?> model = prepareBikePurchaseData();
        return new ModelAndView("/info/bike_purchase", model);
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
        return Map.of(
                "availableBikeDTOs", availableBike,
                "availableBikeSerials", availableBikeSerials,
                "availableSalesmanCodeNameSurnames", availableSalesmanCodeNameSurnames,
                "bikePurchaseDTO", BikePurchaseDTO.buildDefaultData()
        );
    }

    @PostMapping(value = PURCHASE)
    public String makePurchase(
            @Valid @ModelAttribute("bikePurchaseDTO") BikePurchaseDTO bikePurchaseDTO,
            ModelMap model
    ) {
        BikePurchaseRequest request = bikePurchaseMapper.map(bikePurchaseDTO);
        Invoice invoice = bikePurchaseService.purchase(request);

        if (existingCustomerEmailExists(bikePurchaseDTO.getExistingCustomerEmail())) {
            model.addAttribute("existingCustomerEmail", bikePurchaseDTO.getExistingCustomerEmail());
        } else {
            model.addAttribute("customerName", bikePurchaseDTO.getCustomerName());
            model.addAttribute("customerSurname", bikePurchaseDTO.getCustomerSurname());
        }
        model.addAttribute("invoiceNumber", invoice.getInvoiceNumber());

        return "info/bike_purchase_done";
    }

    private boolean existingCustomerEmailExists(String email) {
        return Objects.nonNull(email) && !email.isBlank();
    }
}
