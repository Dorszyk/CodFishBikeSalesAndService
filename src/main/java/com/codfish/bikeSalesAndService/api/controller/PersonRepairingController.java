package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.*;
import com.codfish.bikeSalesAndService.api.dto.mapper.BikeServiceRequestMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.PartMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.PersonRepairingMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.ServiceMapper;
import com.codfish.bikeSalesAndService.business.BikeServiceProcessingService;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.business.PartCatalogService;
import com.codfish.bikeSalesAndService.business.ServiceCatalogService;
import com.codfish.bikeSalesAndService.domain.BikeServiceProcessingRequest;
import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PartJpaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class PersonRepairingController {
    public static final String PERSON_REPAIRING = "/personRepairing";
    public static final String PERSON_REPAIRING_WORK_UNIT = "/personRepairing/workUnit";

    private final BikeServiceProcessingService bikeServiceProcessingService;
    private final BikeServiceRequestService bikeServiceRequestService;
    private final PartCatalogService partCatalogService;
    private final ServiceCatalogService serviceCatalogService;
    private final BikeServiceRequestMapper bikeServiceRequestMapper;
    private final PersonRepairingMapper personRepairingMapper;
    private final PartMapper partMapper;
    private final ServiceMapper serviceMapper;
    private final PartJpaRepository partJpaRepository;


    @GetMapping(value = PERSON_REPAIRING)
    public ModelAndView personRepairingCheckPage() {
        Map<String, ?> data = prepareNecessaryData();
        return new ModelAndView("info/person_repairing_service", data);
    }
    private Map<String, ?> prepareNecessaryData() {
        var availableServiceRequests = getAvailableServiceRequests();
        var availableBikeSerials = availableServiceRequests.stream().map(BikeServiceRequestDTO::getBikeSerial).toList();
        var availablePersonRepairing = getAvailablePersonRepairing();
        var availablePersonRepairingCodeNameSurnames = availablePersonRepairing.stream().map(PersonRepairingDTO::getCodeNameSurname).toList();
        var parts = findParts();
        var description = findParts();
        var partSerialDescription = preparePartSerialDescription(description);
        var services = findServices();
        var partSerialNumbers = preparePartSerialNumbers(parts);
        var serviceCodes = services.stream().map(ServiceDTO::getServiceCode).toList();

        return Map.of(
                "availableServiceRequestDTOs", availableServiceRequests,
                "availableBikeSerials", availableBikeSerials,
                "availablePersonRepairingDTOs", availablePersonRepairing,
                "availablePersonRepairingCodeNameSurnames", availablePersonRepairingCodeNameSurnames,
                "partDTOs", parts,
                "descriptionDTOs",partSerialDescription,
                "partSerialNumbers", partSerialNumbers,
                "serviceDTOs", services,
                "serviceCodes", serviceCodes,
                "bikeServiceProcessDTO", BikeServicePersonProcessingUnitDTO.buildDefault()
        );
    }
    @PostMapping(value = PERSON_REPAIRING_WORK_UNIT)
    public String PersonRepairingWorkUnit(
            @Valid @ModelAttribute("bikeServiceRequestProcessDTO") BikeServicePersonProcessingUnitDTO dto,
            ModelMap modelMap
    ){
        BikeServiceProcessingRequest request =  bikeServiceRequestMapper.map(dto);
        bikeServiceProcessingService.process(request);
        if(dto.getDone()){
            return "info/person_repairing_service_done";
        }else {
            modelMap.addAllAttributes(prepareNecessaryData());
            return "redirect:/personRepairing";
        }
    }
    private List<BikeServiceRequestDTO> getAvailableServiceRequests() {
        return bikeServiceRequestService.availableServiceRequest().stream()
                .map(bikeServiceRequestMapper::map)
                .toList();
    }
    private List<PersonRepairingDTO> getAvailablePersonRepairing() {
        return bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
    }
    private List<PartDTO> findParts() {
        return partCatalogService.findAll().stream()
                .map(partMapper::map)
                .toList();
    }
    private List<ServiceDTO> findServices() {
        return serviceCatalogService.findAll().stream()
                .map(serviceMapper::map)
                .toList();
    }
    private List<String> preparePartSerialNumbers(List<PartDTO> parts) {

        List<String> partSerialNumbers = new ArrayList<>(parts.stream()
                .map(PartDTO::getSerialNumber)
                .toList());
        partSerialNumbers.add(Part.NONE);
        return partSerialNumbers;

    }

    private List<String> preparePartSerialDescription(List<PartDTO> parts) {

        List<String> partPartSerialDescription = new ArrayList<>(parts.stream()
                .map(PartDTO::getDescription)
                .toList());
        partPartSerialDescription.add(Part.NONE);
        return partPartSerialDescription;

    }
}


