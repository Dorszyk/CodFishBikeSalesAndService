package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.PartDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.PartMapper;
import com.codfish.bikeSalesAndService.business.PartCatalogService;
import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PartJpaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@AllArgsConstructor
public class AddUpdatePartsController {


    private static final String PARTS_ADD_UPDATE = "/add_update_parts";
    private static final String PARTS_ADD = "/add_part";
    private static final String PARTS_UPDATE = "/update_part";
    private static final String PARTS_DELETE = "/delete_part";

    private final PartCatalogService partCatalogService;
    private final PartMapper partMapper;
    private final PartJpaRepository partJpaRepository;

    @GetMapping(value = PARTS_ADD_UPDATE)
    public ModelAndView partsAddUpdatePartPage() {
        var model = new ModelAndView("info/add_update_parts");
        List<PartDTO> parts = findParts();
        model.addObject("partDTOs", parts);
        model.addObject("partSerialNumbers", preparePartSerialNumbers(parts));
        return model;
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

    @PostMapping(value = PARTS_ADD)
    public String addPart(@Valid @ModelAttribute("partDTOs") PartDTO partDTO, Model model) {
        Optional<PartEntity> existingPart = partJpaRepository.findBySerialNumber(partDTO.getSerialNumber());
        if (existingPart.isPresent()) {
            throw new ProcessingException("Part already exists: [%s]".formatted(partDTO.getSerialNumber()));
        } else {
            PartEntity newParts = createPartEntity(partDTO);
            partJpaRepository.save(newParts);
        }
        model.addAttribute("partDTOs", findParts());
        return "info/add_part";
    }

    private PartEntity createPartEntity(PartDTO partDTO) {
        return PartEntity.builder()
                .serialNumber(partDTO.getSerialNumber())
                .description(partDTO.getDescription())
                .price(partDTO.getPrice())
                .build();
    }

    @PutMapping(value = PARTS_UPDATE)
    public String updatePart(
            @Valid @ModelAttribute("partDTOs") PartDTO partDTO,
            Model model
    ) {
        PartEntity partToUpdate = partJpaRepository.findBySerialNumber(partDTO.getSerialNumber())
                .orElseThrow(() -> new NotFoundException(
                        generateErrorMessage(partDTO.getSerialNumber())));
        updatePartEntity(partToUpdate, partDTO);
        partJpaRepository.save(partToUpdate);

        model.addAttribute("partDTOs", findParts());
        return "info/update_part";
    }

    private String generateErrorMessage(String identifier) {
        return "%s not found, identifier: [%s]".formatted("Part", identifier);
    }

    private void updatePartEntity(PartEntity partToUpdate, PartDTO partDTO) {
        partToUpdate.setSerialNumber(partDTO.getSerialNumber());
        partToUpdate.setDescription(partDTO.getDescription());
        partToUpdate.setPrice(partDTO.getPrice());
    }

    @DeleteMapping(value = PARTS_DELETE)
    public String deletePart(
            @RequestParam("serialNumber") String serialNumber
    ) {

        try {
            partCatalogService.deletePart(serialNumber);
            log.info("Part deleted successfully");
        } catch (NotFoundException e) {
            log.error("Error deleting part: {}", e.getMessage());
        }
        return "info/delete_part";
    }
}
