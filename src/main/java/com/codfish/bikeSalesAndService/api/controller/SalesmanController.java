package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikeToBuyDTO;
import com.codfish.bikeSalesAndService.api.dto.PersonRepairingDTO;
import com.codfish.bikeSalesAndService.api.dto.SalesmanDTO;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ModelAndView displaySalesmanHomePage() {
        Map<String, Object> model = prepareSalesmanPortalData();
        return new ModelAndView("info/salesman_portal", model);
    }

    private Map<String, Object> prepareSalesmanPortalData() {
        var availableBikeDTOs = mapAvailableBikes();
        var availableSalesmenDTOs = mapAvailableSalesmen();
        var availablePersonRepairingDTOs = mapAvailablePersonRepairing();

        return Map.of(
                "availableBikeDTOs", availableBikeDTOs,
                "availableSalesmenDTOs", availableSalesmenDTOs,
                "availablePersonRepairingDTOs", availablePersonRepairingDTOs,
                "bikeToBuyDTO", BikeToBuyDTO.buildDefault()
        );
    }

    private List<BikeToBuyDTO> mapAvailableBikes() {
        return bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .collect(Collectors.toList());
    }

    private List<SalesmanDTO> mapAvailableSalesmen() {
        return bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .collect(Collectors.toList());
    }

    private List<PersonRepairingDTO> mapAvailablePersonRepairing() {
        return bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping(value = ADD_BIKE)
    public String addBike(@Valid @ModelAttribute("availableBikeDTOs") BikeToBuyDTO bikeDTO, Model model) {
        BikeToBuyEntity newBike = constructBikeEntity(bikeDTO);
        bikeToBuyJpaRepository.save(newBike);
        updateAvailableBikes(model);
        return "info/add_bike";
    }

    private void validateBikeExistence(BikeToBuyDTO bikeDTO) {
        bikeToBuyJpaRepository.findBySerial(bikeDTO.getSerial())
                .ifPresent(existingBike -> {
                    throw new ProcessingException(
                            String.format("Bike with serial: [%s] already exists in the database.", bikeDTO.getSerial()));
                });
    }

    private BikeToBuyEntity constructBikeEntity(BikeToBuyDTO bikeDTO) {
        validateBikeExistence(bikeDTO);
        return BikeToBuyEntity.builder()
                .serial(bikeDTO.getSerial())
                .category(bikeDTO.getCategory())
                .subcategory(bikeDTO.getSubcategory())
                .brand(bikeDTO.getBrand())
                .model(bikeDTO.getModel())
                .year(bikeDTO.getYear())
                .color(bikeDTO.getColor())
                .price(bikeDTO.getPrice())
                .build();
    }

    private void updateAvailableBikes(Model model) {
        var availableBikes = mapAvailableBikes();
        model.addAttribute("availableBikeDTOs", availableBikes);
    }

    @PutMapping(value = UPDATE_BIKE)
    public String updateBike(
            @Valid @ModelAttribute("availableBikeDTOs") BikeToBuyDTO bikeDTO,
            Model model) {
        updateBikeDetails(bikeDTO);
        model.addAttribute("availableBikeDTOs", getAvailableBikeDTOs());
        return "info/update_bike";
    }

    private void updateBikeDetails(BikeToBuyDTO bikeDTO) {
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
    }

    private List<BikeToBuyDTO> getAvailableBikeDTOs() {
        return bikePurchaseService.availableBikes().stream()
                .map(bikeMapper::map)
                .toList();
    }

    @DeleteMapping(value = DELETE_BIKE)
    public String deleteBike(@RequestParam("serial") String serial, Model model) {
        bikeService.deleteBike(serial);
        List<BikeToBuyDTO> availableBikes = getAvailableBikeDTOs();
        model.addAttribute("availableBikeDTOs", availableBikes);
        return "info/delete_bike_done";
    }
}