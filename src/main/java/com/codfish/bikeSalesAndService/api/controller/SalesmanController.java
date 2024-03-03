package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikeToBuyDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.PersonRepairingMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.SalesmanMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.business.BikeService;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.BikeToBuyJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SalesmanController {

    private static final String SALESMAN = "/salesman";
    private static final String ADD_BIKE = "/add_bike";
    private static final String UPDATE_BIKE = "/update_bike";
    private static final String DELETE_BIKE = "/delete_bike";

    private final BikePurchaseService bikePurchaseService;
    private final BikeServiceRequestService bikeServiceRequestService;
    private final BikeService bikeService;
    private final BikeMapper bikeMapper;
    private final SalesmanMapper salesmanMapper;
    private final PersonRepairingMapper personRepairingMapper;
    private final BikeToBuyJpaRepository bikeToBuyJpaRepository;

    @GetMapping(value = SALESMAN)
    public ModelAndView homePage() {
        Map<String, Object> model = prepareSalesmanPortalData();
        return new ModelAndView("info/salesman_portal", model);
    }

    private Map<String, Object> prepareSalesmanPortalData() {
        var availableBikes = bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        var availablePersonRepairing = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
        return Map.of(
                "availableBikeDTOs", availableBikes,
                "availableSalesmenDTOs", availableSalesmen,
                "availablePersonRepairingDTOs", availablePersonRepairing,
                "bikeToBuyDTO", BikeToBuyDTO.buildDefault()
        );
    }

    @PostMapping(value = ADD_BIKE)
    public String addBike(
            @Valid @ModelAttribute("availableBikeDTOs") BikeToBuyDTO bikeDTO, Model model
    ) {

        Optional<BikeToBuyEntity> existingBike = bikeToBuyJpaRepository.findBySerial(bikeDTO.getSerial());
        if (existingBike.isPresent()) {
            throw new ProcessingException(
                    "Bike with serial: [%s] already exists in the database.".formatted(bikeDTO.getSerial()));
        }
        BikeToBuyEntity newBike = BikeToBuyEntity.builder()
                .serial(bikeDTO.getSerial())
                .category(bikeDTO.getCategory())
                .subcategory(bikeDTO.getSubcategory())
                .brand(bikeDTO.getBrand())
                .model(bikeDTO.getModel())
                .year(bikeDTO.getYear())
                .color(bikeDTO.getColor())
                .price(bikeDTO.getPrice())
                .build();
        bikeToBuyJpaRepository.save(newBike);

        var availableBikes = bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
        model.addAttribute("availableBikeDTOs", availableBikes);
        return "info/add_bike";
    }

    @PutMapping(value = UPDATE_BIKE)
    public String updateBike(
            @Valid @ModelAttribute("availableBikeDTOs") BikeToBuyDTO bikeDTO,
            Model model
    ) {
        BikeToBuyEntity bikeToUpdate = bikeToBuyJpaRepository.findBySerial(bikeDTO.getSerial())
                .orElseThrow(() -> new NotFoundException(
                        "Serial Bike not found, serial: [%s]".formatted(bikeDTO.getSerial())));
        bikeToUpdate.setCategory(bikeDTO.getCategory());
        bikeToUpdate.setSubcategory(bikeDTO.getSubcategory());
        bikeToUpdate.setBrand(bikeDTO.getBrand());
        bikeToUpdate.setModel(bikeDTO.getModel());
        bikeToUpdate.setYear(bikeDTO.getYear());
        bikeToUpdate.setColor(bikeDTO.getColor());
        bikeToUpdate.setPrice(bikeDTO.getPrice());
        bikeToBuyJpaRepository.save(bikeToUpdate);

        var availableBikes = bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
        model.addAttribute("availableBikeDTOs", availableBikes);

        return "info/update_bike";
    }

    @DeleteMapping(value = DELETE_BIKE)
    public String deleteBike(
            @RequestParam("serial") String serial, Model model
    ) {
        bikeService.deleteBike(serial);
        var availableBikes = bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
        model.addAttribute("availableBikeDTOs", availableBikes);
        return "info/delete_bike_done";
    }
}
