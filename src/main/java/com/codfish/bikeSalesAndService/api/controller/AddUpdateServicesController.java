package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.BikeServicePersonProcessingUnitDTO;
import com.codfish.bikeSalesAndService.api.dto.ServiceDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.ServiceMapper;
import com.codfish.bikeSalesAndService.business.ServiceCatalogService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.ServiceJpaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class AddUpdateServicesController {

    private static final String SERVICES_ADD_UPDATE = "/add_update_services";
    private static final String SERVICE_ADD = "/add_service";
    private static final String SERVICE_UPDATE = "/update_service";
    private static final String SERVICE_DELETE = "/delete_service";

    private final ServiceCatalogService serviceCatalogService;
    private final ServiceMapper serviceMapper;
    private final ServiceJpaRepository serviceJpaRepository;

    @GetMapping(value = SERVICES_ADD_UPDATE)
    public ModelAndView serviceAddUpdateServicePage() {
        Map<String, ?> data = prepareNecessaryData();
        return new ModelAndView("info/add_update_services", data);
    }

    private Map<String, ?> prepareNecessaryData() {
        var services = findServices();
        var serviceCodes = services.stream().map(ServiceDTO::getServiceCode).toList();

        return Map.of(
                "serviceDTOs", services,
                "serviceCodes", serviceCodes,
                "bikeServiceProcessDTO", BikeServicePersonProcessingUnitDTO.buildDefault()
        );
    }

    @PostMapping(value = SERVICE_ADD)
    public String addPart(
            @Valid @ModelAttribute("serviceDTOs") ServiceDTO serviceDTO, Model model) {
        ServiceEntity newService = ServiceEntity.builder()
                .serviceCode(serviceDTO.getServiceCode())
                .description(serviceDTO.getDescription())
                .price(serviceDTO.getPrice())
                .build();
        serviceJpaRepository.save(newService);
        var availableService = serviceCatalogService.findAll().stream()
                .map(serviceMapper::map)
                .toList();
        model.addAttribute("serviceDTOs", availableService);
        return "info/add_service";
    }

    @PutMapping(value = SERVICE_UPDATE)
    public String updatePart(
            @Valid @ModelAttribute("serviceDTOs") ServiceDTO serviceDTO,
            Model model
    ) {
        ServiceEntity updateService = serviceJpaRepository.findByServiceCode(serviceDTO.getServiceCode())
                .orElseThrow(() -> new NotFoundException(
                        "Service number not found, service: [%s]".formatted(serviceDTO.getServiceCode())));
        updateService.setServiceCode(serviceDTO.getServiceCode());
        updateService.setDescription(serviceDTO.getDescription());
        updateService.setPrice(serviceDTO.getPrice());
        serviceJpaRepository.save(updateService);
        var availableService = serviceCatalogService.findAll().stream()
                .map(serviceMapper::map)
                .toList();
        model.addAttribute("serviceDTOs", availableService);
        return "info/update_service";
    }

    private List<ServiceDTO> findServices() {
        return serviceCatalogService.findAll().stream()
                .map(serviceMapper::map)
                .toList();
    }
    @DeleteMapping(value = SERVICE_DELETE)
    public String deletePart(
            @RequestParam("serviceCode") String serviceCode
    ) {
        var service = serviceJpaRepository.findByServiceCode(serviceCode);
        serviceJpaRepository.delete(service.get());
        return "info/delete_service";
    }

}
