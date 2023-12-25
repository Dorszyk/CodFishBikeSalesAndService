package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.PersonRepairingDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.PersonRepairingMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.UserMapper;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.business.PersonRepairingService;
import com.codfish.bikeSalesAndService.business.UserService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PersonRepairingJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.security.RoleEntity;
import com.codfish.bikeSalesAndService.infrastructure.security.RoleRepository;
import com.codfish.bikeSalesAndService.infrastructure.security.UserEntity;
import com.codfish.bikeSalesAndService.infrastructure.security.UserJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AddUpdatePersonRepairingController {
    private static final String ADD_UPDATE_PERSON_REPAIRING = "/add_update_person_repairing";
    private static final String ADD_PERSON_REPAIRING = "/add_person_repairing";
    private static final String UPDATE_PERSON_REPAIRING = "/update_person_repairing";
    private static final String DELETE_PERSON_REPAIRING = "/delete_person_repairing";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonRepairingService personRepairingService;
    private final PersonRepairingJpaRepository personRepairingJpaRepository;
    private final PersonRepairingMapper personRepairingMapper;
    private final BikeServiceRequestService bikeServiceRequestService;
    private final UserJpaRepository userJpaRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping(value = ADD_UPDATE_PERSON_REPAIRING)
    public String personRepairingAddUpdatePage(Model model) {
        var availablePersonRepairing = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
        var availableUser = userService.findAllUsers()
                .stream()
                .map(userMapper::map)
                .toList();
        model.addAttribute("availableUserDTOs", availableUser);
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
        int nextUserId = userJpaRepository.findMaxUserId().orElse(0) + 1;

        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity personRepairingRole = roleRepository.findByRole("PERSON_REPAIRING");
        roles.add(personRepairingRole);

        String hashedPassword = bCryptPasswordEncoder.encode(personRepairingDTO.getPassword());
        UserEntity newUser = UserEntity.builder()
                .userName(personRepairingDTO.getUserName())
                .email(personRepairingDTO.getEmail())
                .password(hashedPassword)
                .userId(nextUserId)
                .roles(roles)
                .active(true)
                .build();
        UserEntity savedUser = userJpaRepository.save(newUser);

        PersonRepairingEntity newPersonRepairing = PersonRepairingEntity.builder()
                .name(personRepairingDTO.getName())
                .surname(personRepairingDTO.getSurname())
                .codeNameSurname(personRepairingDTO.getCodeNameSurname())
                .userId(savedUser.getUserId())
                .build();
        personRepairingJpaRepository.save(newPersonRepairing);

        updateModelWithAvailablePersonRepairing(model);
        return "info/add_person_repairing_done";
    }

    private void updateModelWithAvailablePersonRepairing(Model model){
        var availablePersonRepairing = bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
        model.addAttribute("availablePersonRepairingDTOs", availablePersonRepairing);
    }

    @RequestMapping(value = UPDATE_PERSON_REPAIRING)
    public String updatePersonRepairing(
            @Valid @ModelAttribute("updatePersonRepairingDTO") PersonRepairingDTO personRepairingDTO, Model model
    ) {
        PersonRepairingEntity personRepairingToUpdate = personRepairingJpaRepository.findByCodeNameSurname(personRepairingDTO.getCodeNameSurname())
                .orElseThrow(() -> new NotFoundException(
                        "Person repairing not found, CodeNameSurname: [%s]".formatted(personRepairingDTO.getCodeNameSurname())));

        UserEntity userToUpdate = userJpaRepository.findById(personRepairingToUpdate.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        "User not found for given person repairing, UserId: [%d]".formatted(personRepairingToUpdate.getUserId())));

        personRepairingToUpdate.setCodeNameSurname(personRepairingDTO.getCodeNameSurname());
        personRepairingToUpdate.setName(personRepairingDTO.getName());
        personRepairingToUpdate.setSurname(personRepairingDTO.getSurname());


        userToUpdate.setUserName(personRepairingDTO.getUserName());
        userToUpdate.setEmail(personRepairingDTO.getEmail());
        if (personRepairingDTO.getPassword() != null && !personRepairingDTO.getPassword().isEmpty()) {
            String hashedPassword = bCryptPasswordEncoder.encode(personRepairingDTO.getPassword());
            userToUpdate.setPassword(hashedPassword);
        }

        userJpaRepository.save(userToUpdate);
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
