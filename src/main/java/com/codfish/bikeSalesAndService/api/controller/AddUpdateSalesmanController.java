package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.SalesmanDTO;
import com.codfish.bikeSalesAndService.api.dto.UserDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.SalesmanMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.UserMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.business.SalesmanService;
import com.codfish.bikeSalesAndService.business.UserService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.SalesmanEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.SalesmanJpaRepository;
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
public class AddUpdateSalesmanController {

    private static final String ADD_UPDATE_SALESMAN = "/add_update_salesman";
    private static final String ADD_SALESMAN = "/add_salesman";
    private static final String UPDATE_SALESMAN = "/update_salesman";
    private static final String DELETE_SALESMAN = "/delete_salesman";
    private static final String SALESMAN_ROLE_NAME = "SALESMAN";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BikePurchaseService bikePurchaseService;
    private final SalesmanMapper salesmanMapper;
    private final SalesmanJpaRepository salesmanJpaRepository;
    private final SalesmanService salesmanService;
    private final UserJpaRepository userJpaRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(value = ADD_UPDATE_SALESMAN)
    public String salesmanAddUpdatePage(Model model) {
        addAttributesToModel(model, getAvailableUsers(), getAvailableSalesmen());
        return "info/add_update_salesman";
    }

    private List<UserDTO> getAvailableUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    private List<SalesmanDTO> getAvailableSalesmen() {
        return bikePurchaseService.availableSalesmen()
                .stream()
                .map(salesmanMapper::map)
                .toList();
    }

    private void addAttributesToModel(Model model, List<UserDTO> availableUser, List<SalesmanDTO> availableSalesmen) {
        model.addAttribute("availableUserDTOs", availableUser);
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);
    }

    @PostMapping(value = ADD_SALESMAN)
    public String addSalesman
            (@Valid @ModelAttribute("newSalesmanDTO") SalesmanDTO salesmanDTO, Model model
            ) {
        checkSalesmenAlreadyExists(salesmanDTO.getCodeNameSurname());
        UserEntity savedUser = createAndSaveUser(salesmanDTO);
        createAndSaveSalesman(salesmanDTO, savedUser.getUserId());
        addAttributesToModel(model, getAvailableUsers(), getAvailableSalesmen());
        return "info/add_salesman_done";
    }

    private void checkSalesmenAlreadyExists(String codeNameSurname) {
        Optional<SalesmanEntity> existingSalesman = salesmanJpaRepository.findByCodeNameSurname(codeNameSurname);
        if (existingSalesman.isPresent()) {
            throw new ProcessingException(
                    "Salesman already exists: [%s]".formatted(codeNameSurname));
        }
    }

    private UserEntity createAndSaveUser(SalesmanDTO salesmanDTO) {
        int nextUserId = userJpaRepository.findMaxUserId().orElse(0) + 1;
        String hashedPassword = bCryptPasswordEncoder.encode(salesmanDTO.getPassword());
        UserEntity newUser = UserEntity.builder()
                .userName(salesmanDTO.getUserName())
                .email(salesmanDTO.getEmail())
                .password(hashedPassword)
                .userId(nextUserId)
                .roles(Collections.singleton(roleRepository.findByRole(SALESMAN_ROLE_NAME)))
                .active(true)
                .build();
        return userJpaRepository.save(newUser);
    }

    private void createAndSaveSalesman(SalesmanDTO salesmanDTO, int userId) {
        SalesmanEntity newSalesman = SalesmanEntity.builder()
                .name(salesmanDTO.getName())
                .surname(salesmanDTO.getSurname())
                .codeNameSurname(salesmanDTO.getCodeNameSurname())
                .userId(userId)
                .build();
        salesmanJpaRepository.save(newSalesman);
    }

    @PutMapping(value = UPDATE_SALESMAN)
    public String updateSalesman(
            @Valid @ModelAttribute("salesmanDTO") SalesmanDTO salesmanDTO, Model model) {

        var salesmanToUpdate = findSalesmanEntity(salesmanDTO);
        var userToUpdate = findUserEntity(salesmanToUpdate);

        updateSalesmanEntity(salesmanDTO, salesmanToUpdate);
        updateUserEntity(salesmanDTO, userToUpdate);

        userJpaRepository.save(userToUpdate);
        salesmanJpaRepository.save(salesmanToUpdate);

        addAttributesToModel(model, getAvailableUsers(), getAvailableSalesmen());

        return "info/update_salesman_done";
    }

    private SalesmanEntity findSalesmanEntity(SalesmanDTO salesmanDTO) {
        return salesmanJpaRepository.findByCodeNameSurname(salesmanDTO.getCodeNameSurname())
                .orElseThrow(() -> new NotFoundException(
                        generateErrorMessage("Salesman", salesmanDTO.getCodeNameSurname())));
    }

    private UserEntity findUserEntity(SalesmanEntity salesmanToUpdate) {
        return userJpaRepository.findById(salesmanToUpdate.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        generateErrorMessage("User", Long.toString(salesmanToUpdate.getUserId()))));
    }

    private void updateSalesmanEntity(SalesmanDTO dto, SalesmanEntity entity) {
        entity.setCodeNameSurname(dto.getCodeNameSurname());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
    }

    private void updateUserEntity(SalesmanDTO dto, UserEntity entity) {
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
    }

    private String generateErrorMessage(String entityName, String identifier) {
        return "%s not found, identifier: [%s]".formatted(entityName, identifier);
    }

    @DeleteMapping(value = DELETE_SALESMAN)
    public String deleteSalesman(
            @RequestParam("codeNameSurname") String codeNameSurname, Model model
    ) {
        salesmanService.deleteSalesmanByCodeNameSurname(codeNameSurname);
        addAttributesToModel(model, getAvailableUsers(), getAvailableSalesmen());
        return "info/delete_salesman_done";
    }
}
