package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.PersonRepairingDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.PersonRepairingMapper;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.business.PersonRepairingService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PersonRepairingJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AddUpdatePersonRepairingController {
    private static final String ADD_UPDATE_PERSON_REPAIRING = "/add_update_person_repairing";
    private static final String ADD_PERSON_REPAIRING = "/add_person_repairing";
    private static final String UPDATE_PERSON_REPAIRING = "/update_person_repairing";
    private static final String DELETE_PERSON_REPAIRING = "/delete_person_repairing";
    
  
    private final PersonRepairingService personRepairingService;
    private final PersonRepairingJpaRepository personRepairingJpaRepository;
    private final PersonRepairingMapper personRepairingMapper;
    private final BikeServiceRequestService bikeServiceRequestService;


    @GetMapping(value = ADD_UPDATE_PERSON_REPAIRING)
    public String personRepairingAddUpdatePage(Model model) {
        var availablePersonRepairing = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
        model.addAttribute("availablePersonRepairingDTOs", availablePersonRepairing);
        return "info/add_update_person_repairing";
    }

    @PostMapping(value = ADD_PERSON_REPAIRING)
    public String addPersonRepairing(
            @ModelAttribute("newPersonRepairingDTO") PersonRepairingDTO personRepairingDTO, Model model
    ) {
        Optional<PersonRepairingEntity> existingPersonRepairing = personRepairingJpaRepository.findByCodeNameSurname(personRepairingDTO.getCodeNameSurname());
        if (existingPersonRepairing.isPresent()) {
            throw new ProcessingException(
                    "Person Repairing already exists, CodeNameSurname: [%s]".formatted(personRepairingDTO.getCodeNameSurname()));
        }
        Integer nextUserId = personRepairingJpaRepository.findMaxUserId().orElse(0) + 1;

        PersonRepairingEntity newPersonRepairing = PersonRepairingEntity.builder()
                .name(personRepairingDTO.getName())
                .surname(personRepairingDTO.getSurname())
                .codeNameSurname(personRepairingDTO.getCodeNameSurname())
                .userId(nextUserId)
                .build();
        personRepairingJpaRepository.save(newPersonRepairing);

        var availablePersonRepairing = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
        model.addAttribute("availablePersonRepairingDTOs", availablePersonRepairing);

        return "info/add_person_repairing_done";
    }

    @RequestMapping(value = UPDATE_PERSON_REPAIRING)
    public String updatePersonRepairing(
            @Valid @ModelAttribute("updatePersonRepairingDTO") PersonRepairingDTO  personRepairingDTO, Model model
    ) {
        PersonRepairingEntity personRepairingToUpdate = personRepairingJpaRepository.findByCodeNameSurname(personRepairingDTO.getCodeNameSurname())
                .orElseThrow(() -> new NotFoundException(
                        "Person repairing not found, CodeNameSurname: [%s]".formatted(personRepairingDTO.getCodeNameSurname())));
        personRepairingToUpdate.setCodeNameSurname(personRepairingDTO.getCodeNameSurname());
        personRepairingToUpdate.setName(personRepairingDTO.getName());
        personRepairingToUpdate.setSurname(personRepairingDTO.getSurname());
        personRepairingJpaRepository.save(personRepairingToUpdate);

        var availablePersonRepairing = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
        model.addAttribute("availablePersonRepairingDTOs", availablePersonRepairing);
        return "info/update_person_repairing_done";
    }

    @PostMapping(value = DELETE_PERSON_REPAIRING)
    public String deletePersonRepairing(
            @RequestParam("codeNameSurname") String codeNameSurname, Model model
    ) {
        personRepairingService.deletePersonRepairingByCodeNameSurname(codeNameSurname);

        var availablePersonRepairing = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
        model.addAttribute("availablePersonRepairingDTOs", availablePersonRepairing);

        return "info/delete_person_repairing_done";
    }
}
