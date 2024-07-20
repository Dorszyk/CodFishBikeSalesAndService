package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.ServiceDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.ServiceMapper;
import com.codfish.bikeSalesAndService.business.ServiceCatalogService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.ServiceJpaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AddUpdateServicesController {

    private static final String SERVICES_ADD_UPDATE = "/add_update_services";
    private static final String SERVICE_ADD = "/add_service";
    private static final String SERVICE_UPDATE = "/update_service";
    private static final String SERVICE_DELETE = "/delete_service";
    private static final Logger log = LoggerFactory.getLogger(AddUpdateServicesController.class);

    private final ServiceCatalogService serviceCatalogService;
    private final ServiceMapper serviceMapper;
    private final ServiceJpaRepository serviceJpaRepository;

    @GetMapping(value = SERVICES_ADD_UPDATE)
    public ModelAndView serviceAddUpdateServicePage() {
        var model = new ModelAndView("info/add_update_services");
        List<ServiceDTO> services = findServices();
        model.addObject("serviceDTOs", services);
        model.addObject("serviceCodes", extractServiceCodes(services));
        return model;
    }

    private List<ServiceDTO> findServices() {
        return serviceCatalogService.findAll().stream()
                .map(serviceMapper::map)
                .toList();
    }

    private List<String> extractServiceCodes(List<ServiceDTO> services) {
        return services.stream()
                .map(ServiceDTO::getServiceCode)
                .toList();
    }

    @PostMapping(value = SERVICE_ADD)
    public String addServices(
            @Valid @ModelAttribute("serviceDTOs") ServiceDTO serviceDTO, Model model) {
        Optional<ServiceEntity> existingService = serviceJpaRepository.findByServiceCode(serviceDTO.getServiceCode());
        if (existingService.isPresent()) {
            throw new ProcessingException("Service already exists: [%s]".formatted(serviceDTO.getServiceCode()));
        } else {
            ServiceEntity newService = createServiceEntity(serviceDTO);
            serviceJpaRepository.save(newService);
        }
        model.addAttribute("serviceDTOs", findServices());
        return "info/add_service";
    }

    private ServiceEntity createServiceEntity(ServiceDTO serviceDTO) {

        return ServiceEntity.builder()
                .serviceCode(serviceDTO.getServiceCode())
                .description(serviceDTO.getDescription())
                .price(serviceDTO.getPrice())
                .build();
    }

    @PutMapping(value = SERVICE_UPDATE)
    public String updateService(
            @Valid @ModelAttribute("serviceDTOs") ServiceDTO serviceDTO,
            Model model
    ) {
        ServiceEntity updateService = serviceJpaRepository.findByServiceCode(serviceDTO.getServiceCode())
                .orElseThrow(() -> new NotFoundException(
                        generateErrorMessage(serviceDTO.getServiceCode())));
        updateServiceEntity(updateService, serviceDTO);
        serviceJpaRepository.save(updateService);

        model.addAttribute("serviceDTOs", findServices());
        return "info/update_service";
    }

    private void updateServiceEntity(ServiceEntity serviceToUpdate, ServiceDTO serviceDTO) {
        serviceToUpdate.setServiceCode(serviceDTO.getServiceCode());
        serviceToUpdate.setDescription(serviceDTO.getDescription());
        serviceToUpdate.setPrice(serviceDTO.getPrice());
    }

    private String generateErrorMessage(String identifier) {
        return "%s not found, identifier: [%s]".formatted("Service", identifier);
    }

    @DeleteMapping(value = SERVICE_DELETE)
    public String deleteService(
            @RequestParam("serviceCode") String serviceCode
    ) {
        try {
            serviceCatalogService.deleteService(serviceCode);
            log.info("Service deleted successfully");
        } catch (NotFoundException e) {
            log.info("Service not found");

        }
        return "info/delete_service";
    }
}
