package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikeHistoryDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeToServiceDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.business.BikeService;
import com.codfish.bikeSalesAndService.domain.BikeHistory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class BikeHistoryController {

    private static final String BIKE_HISTORY = "/bike/history";

    private final BikeService bikeService;
    private final BikeMapper bikeMapper;

    @GetMapping(value = BIKE_HISTORY)
    public String bikeHistory(
            @RequestParam(value = "bikeSerial", required = false) String bikeSerial,
            Model model
    ) {
        var allBike = bikeService.findAllBikesWithHistory().stream().map(bikeMapper::map).toList();
        var allBikeSerials = allBike.stream().map(BikeToServiceDTO::getSerial).toList();

        model.addAttribute("allBikeDTOs", allBike);
        model.addAttribute("allBikeSerials", allBikeSerials);

        if (Objects.nonNull(bikeSerial)) {
            BikeHistory bikeHistory = bikeService.findBikeHistoryBySerial(bikeSerial);
            model.addAttribute("bikeHistoryDTO", bikeMapper.map(bikeHistory));
        } else {
            model.addAttribute("bikeHistoryDTO", BikeHistoryDTO.buildDefault());
        }
        return "bike_history";
    }
}
