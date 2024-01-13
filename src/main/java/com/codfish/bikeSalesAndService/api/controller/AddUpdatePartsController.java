package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.*;
import com.codfish.bikeSalesAndService.api.dto.mapper.PartMapper;
import com.codfish.bikeSalesAndService.business.PartCatalogService;
import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PartJpaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class AddUpdatePartsController {

    public static final String PARTS_ADD_UPDATE = "/add_update_parts";
    public static final String PARTS_ADD = "/add_part";
    public static final String PARTS_UPDATE = "/update_part";
    public static final String PARTS_DELETE = "/delete_part";

    private final PartCatalogService partCatalogService;
    private final PartMapper partMapper;
    private final PartJpaRepository partJpaRepository;

    @GetMapping(value = PARTS_ADD_UPDATE)
    public ModelAndView personRepairingCheckPage() {
        Map<String, ?> data = prepareNecessaryData();
        return new ModelAndView("info/add_update_parts", data);
    }

    private Map<String, ?> prepareNecessaryData() {
        var parts = findParts();
        var partSerialNumbers = preparePartSerialNumbers(parts);


        return Map.of(
                "partDTOs", parts,
                "partSerialNumbers", partSerialNumbers
        );
    }

    @PostMapping(value = PARTS_ADD)
    public String addPart(
            @Valid @ModelAttribute("partDTOs") PartDTO partDTO, Model model) {
        PartEntity newParts = PartEntity.builder()
                .serialNumber(partDTO.getSerialNumber())
                .description(partDTO.getDescription())
                .price(partDTO.getPrice())
                .build();
        partJpaRepository.save(newParts);

        var availableParts = partCatalogService.findAll().stream()
                .map(partMapper::map)
                .toList();
        model.addAttribute("partDTOs", availableParts);
        return "info/add_part";
    }

    @RequestMapping(value = PARTS_UPDATE)
    public String updatePart(
            @Valid @ModelAttribute("partDTOs") PartDTO partDTO,
            Model model
    ) {
        PartEntity updateParts = partJpaRepository.findBySerialNumber(partDTO.getSerialNumber())
                .orElseThrow(() -> new NotFoundException(
                        "Part serial number not found, serial: [%s]".formatted(partDTO.getSerialNumber())));
        updateParts.setSerialNumber(partDTO.getSerialNumber());
        updateParts.setDescription(partDTO.getDescription());
        updateParts.setPrice(partDTO.getPrice());
        partJpaRepository.save(updateParts);
        var availableParts = partCatalogService.findAll().stream()
                .map(partMapper::map)
                .toList();
        model.addAttribute("partDTOs", availableParts);
        return "info/update_part";
    }

    private List<PartDTO> findParts() {
        return partCatalogService.findAll().stream()
                .map(partMapper::map)
                .toList();
    }

    private List<String> preparePartSerialNumbers(List<PartDTO> parts) {

        List<String> partSerialNumbers = new ArrayList<>();
        partSerialNumbers.add(Part.NONE);
        partSerialNumbers.addAll(parts.stream()
                .map(PartDTO::getSerialNumber)
                .toList());
        return partSerialNumbers;
    }

    @PostMapping(value = PARTS_DELETE)
    public String deletePart(
            @RequestParam("serialNumber") String serialNumber
    ) {
        var part = partJpaRepository.findBySerialNumber(serialNumber);
        partJpaRepository.delete(part.get());
        return "info/delete_part";
    }
}
