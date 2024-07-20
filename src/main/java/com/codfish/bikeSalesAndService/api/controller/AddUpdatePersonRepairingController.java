package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.PersonRepairingDTO;
import com.codfish.bikeSalesAndService.api.dto.UserDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.PersonRepairingMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.UserMapper;
import com.codfish.bikeSalesAndService.business.BikeServiceRequestService;
import com.codfish.bikeSalesAndService.business.PersonRepairingService;
import com.codfish.bikeSalesAndService.business.UserService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PersonRepairingJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.security.RoleRepository;
import com.codfish.bikeSalesAndService.infrastructure.security.UserEntity;
import com.codfish.bikeSalesAndService.infrastructure.security.UserJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AddUpdatePersonRepairingController {

    private static final String ADD_UPDATE_PERSON_REPAIRING = "/add_update_person_repairing";
    private static final String ADD_PERSON_REPAIRING = "/add_person_repairing";
    private static final String UPDATE_PERSON_REPAIRING = "/update_person_repairing";
    private static final String DELETE_PERSON_REPAIRING = "/delete_person_repairing";
    private static final String PERSON_REPAIRING_ROLE_NAME = "PERSON_REPAIRING";

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
        addUserAndPersonRepairingToModel(model, getAvailableUsers(), getAvailablePersonRepairing());
        return "info/add_update_person_repairing";
    }

    private List<UserDTO> getAvailableUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::map)
                .toList();
    }

    private List<PersonRepairingDTO> getAvailablePersonRepairing() {
        return bikeServiceRequestService.availablePersonRepairing().stream()
                .map(personRepairingMapper::map)
                .toList();
    }

    private void addUserAndPersonRepairingToModel(
            Model model,
            List<UserDTO> availableUser,
            List<PersonRepairingDTO> availablePersonRepairing) {
        model.addAttribute("availableUserDTOs", availableUser);
        model.addAttribute("availablePersonRepairingDTOs", availablePersonRepairing);
    }

    @PostMapping(value = ADD_PERSON_REPAIRING)
    public String addPersonRepairing(
            @Valid @ModelAttribute("newPersonRepairingDTO") PersonRepairingDTO personRepairingDTO, Model model
    ) {
        checkPersonRepairingAlreadyExists(personRepairingDTO.getCodeNameSurname());
        UserEntity savedUser = createAndSaveUser(personRepairingDTO);
        createAndSavePersonRepairing(personRepairingDTO, savedUser.getUserId());
        addUserAndPersonRepairingToModel(model, getAvailableUsers(), getAvailablePersonRepairing());
        return "info/add_person_repairing_done";
    }

    private void checkPersonRepairingAlreadyExists(String codeNameSurname) {
        Optional<PersonRepairingEntity> existingPersonRepairing = personRepairingJpaRepository.findByCodeNameSurname(codeNameSurname);
        if (existingPersonRepairing.isPresent()) {
            throw new ProcessingException(
                    "Person Repairing already exists: [%s]".formatted(codeNameSurname));
        }
    }

    private UserEntity createAndSaveUser(PersonRepairingDTO personRepairingDTO) {
        int nextUserId = userJpaRepository.findMaxUserId().orElse(0) + 1;
        String hashedPassword = bCryptPasswordEncoder.encode(personRepairingDTO.getPassword());
        UserEntity newUser = UserEntity.builder()
                .userName(personRepairingDTO.getUserName())
                .email(personRepairingDTO.getEmail())
                .password(hashedPassword)
                .userId(nextUserId)
                .roles(Collections.singleton(roleRepository.findByRole(PERSON_REPAIRING_ROLE_NAME)))
                .active(true)
                .build();
        return userJpaRepository.save(newUser);
    }

    private void createAndSavePersonRepairing(PersonRepairingDTO personRepairingDTO, int userId) {
        PersonRepairingEntity newPersonRepairing = PersonRepairingEntity.builder()
                .name(personRepairingDTO.getName())
                .surname(personRepairingDTO.getSurname())
                .codeNameSurname(personRepairingDTO.getCodeNameSurname())
                .userId(userId)
                .build();
        personRepairingJpaRepository.save(newPersonRepairing);
    }

    @PutMapping(value = UPDATE_PERSON_REPAIRING)
    public String updatePersonRepairing(
            @Valid @ModelAttribute("updatePersonRepairingDTO") PersonRepairingDTO personRepairingDTO, Model model) {

        var personRepairingToUpdate = findPersonRepairingEntity(personRepairingDTO);
        var userToUpdate = findUserEntity(personRepairingToUpdate);

        updatePersonRepairingEntity(personRepairingDTO, personRepairingToUpdate);
        updateUserEntity(personRepairingDTO, userToUpdate);

        userJpaRepository.save(userToUpdate);
        personRepairingJpaRepository.save(personRepairingToUpdate);

        addUserAndPersonRepairingToModel(model, getAvailableUsers(), getAvailablePersonRepairing());

        return "info/update_person_repairing_done";
    }

    private PersonRepairingEntity findPersonRepairingEntity(PersonRepairingDTO personRepairingDTO) {
        return personRepairingJpaRepository.findByCodeNameSurname(personRepairingDTO.getCodeNameSurname())
                .orElseThrow(() -> new NotFoundException(
                        generateErrorMessage("Person repairing", personRepairingDTO.getCodeNameSurname())));
    }

    private UserEntity findUserEntity(PersonRepairingEntity personRepairingToUpdate) {
        return userJpaRepository.findById(personRepairingToUpdate.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        generateErrorMessage("User", Long.toString(personRepairingToUpdate.getUserId()))));
    }

    private void updatePersonRepairingEntity(PersonRepairingDTO dto, PersonRepairingEntity entity) {
        entity.setCodeNameSurname(dto.getCodeNameSurname());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
    }

    private void updateUserEntity(PersonRepairingDTO dto, UserEntity entity) {
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
    }

    private String generateErrorMessage(String entityName, String identifier) {
        return "%s not found, identifier: [%s]".formatted(entityName, identifier);
    }

    @DeleteMapping(value = DELETE_PERSON_REPAIRING)
    public String deletePersonRepairing(
            @RequestParam("codeNameSurname") String codeNameSurname, Model model
    ) {
        personRepairingService.deletePersonRepairingByCodeNameSurname(codeNameSurname);
        addUserAndPersonRepairingToModel(model, getAvailableUsers(), getAvailablePersonRepairing());
        return "info/delete_person_repairing_done";
    }
}
