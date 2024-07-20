package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikeHistoryDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeToServiceDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeMapper;
import com.codfish.bikeSalesAndService.business.BikeService;
import com.codfish.bikeSalesAndService.domain.BikeToService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BikeHistoryController {

    private static final String BIKE_HISTORY = "/bike/history";
    private final BikeService bikeService;
    private final BikeMapper bikeMapper;

    @GetMapping(value = BIKE_HISTORY)
    public ModelAndView bikeHistory(@RequestParam(value = "bikeSerial", required = false) String bikeSerial) {
        var model = new ModelAndView("info/bike_history");
        var allBikeDTOs = getAllBikesWithHistory();
        model.addObject("allBikeDTOs", allBikeDTOs);
        model.addObject("allBikeSerials", getAllBikeSerialsWithHistory(allBikeDTOs));
        addBikeHistoryToModel(bikeSerial, model);
        return model;
    }

    private List<BikeToServiceDTO> getAllBikesWithHistory() {
        return bikeService.findAllBikesWithHistory()
                .stream()
                .map(this::mapBikeToDTO)
                .toList();
    }

    private BikeToServiceDTO mapBikeToDTO(BikeToService bike) {
        return bikeMapper.map(bike);
    }

    private List<String> getAllBikeSerialsWithHistory(List<BikeToServiceDTO> allBikesWithHistory) {
        return allBikesWithHistory
                .stream()
                .map(BikeToServiceDTO::getSerial)
                .toList();
    }

    private void addBikeHistoryToModel(String bikeSerial, ModelAndView model) {
        BikeHistoryDTO bikeHistoryDTO = Optional.ofNullable(bikeSerial)
                .map(bikeService::findBikeHistoryBySerial)
                .map(bikeMapper::map)
                .orElse(BikeHistoryDTO.buildDefault());
        model.addObject("bikeHistoryDTO", bikeHistoryDTO);
    }
}