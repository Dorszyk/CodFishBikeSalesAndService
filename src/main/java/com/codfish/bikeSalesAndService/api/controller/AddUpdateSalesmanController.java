package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.SalesmanDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.SalesmanMapper;
import com.codfish.bikeSalesAndService.api.dto.mapper.UserMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.business.SalesmanService;
import com.codfish.bikeSalesAndService.business.UserService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.SalesmanEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.SalesmanJpaRepository;
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
public class AddUpdateSalesmanController {
    private static final String ADD_UPDATE_SALESMAN = "/add_update_salesman";
    private static final String ADD_SALESMAN = "/add_salesman";
    private static final String UPDATE_SALESMAN = "/update_salesman";
    private static final String DELETE_SALESMAN = "/delete_salesman";

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
        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        var availableUser = userService.findAllUsers()
                .stream()
                .map(userMapper::map)
                .toList();
        model.addAttribute("availableUserDTOs", availableUser);
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);
        return "info/add_update_salesman";
    }

    @PostMapping(value = ADD_SALESMAN)
    public String addSalesman(
            @ModelAttribute("newSalesmanDTO") SalesmanDTO salesmanDTO, Model model
    ) {

        Optional<SalesmanEntity> existingSalesman = salesmanJpaRepository.findByCodeNameSurname(salesmanDTO.getCodeNameSurname());
        if (existingSalesman.isPresent()) {
            throw new ProcessingException(
                    "Salesman already exists, CodeNameSurname: [%s]".formatted(salesmanDTO.getCodeNameSurname()));
        }

        int nextUserId = userJpaRepository.findMaxUserId().orElse(0) + 1;

        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity salesmanRole = roleRepository.findByRole("SALESMAN");
        roles.add(salesmanRole);

        String hashedPassword = bCryptPasswordEncoder.encode(salesmanDTO.getPassword());
        UserEntity newUser = UserEntity.builder()
                .userName(salesmanDTO.getUserName())
                .email(salesmanDTO.getEmail())
                .password(hashedPassword)
                .userId(nextUserId)
                .roles(roles)
                .active(true)
                .build();
        UserEntity savedUser = userJpaRepository.save(newUser);

        SalesmanEntity newSalesman = SalesmanEntity.builder()
                .name(salesmanDTO.getName())
                .surname(salesmanDTO.getSurname())
                .codeNameSurname(salesmanDTO.getCodeNameSurname())
                .userId(savedUser.getUserId())
                .build();
        salesmanJpaRepository.save(newSalesman);

        updateModelWithAvailableSalesmen(model);
        return "info/add_salesman_done";
    }

    private void updateModelWithAvailableSalesmen(Model model) {
        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);
    }

    @RequestMapping(value = UPDATE_SALESMAN)
    public String updateSalesman(
            @Valid @ModelAttribute("salesmanDTO") SalesmanDTO salesmanDTO, Model model
    ) {

        SalesmanEntity salesmanToUpdate = salesmanJpaRepository.findByCodeNameSurname(salesmanDTO.getCodeNameSurname())
                .orElseThrow(() -> new NotFoundException(
                        "Salesman not found, CodeNameSurname: [%s]".formatted(salesmanDTO.getCodeNameSurname())));

        UserEntity userToUpdate = userJpaRepository.findById(salesmanToUpdate.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        "User not found for given salesman, UserId: [%d]".formatted(salesmanToUpdate.getUserId())));

        salesmanToUpdate.setCodeNameSurname(salesmanDTO.getCodeNameSurname());
        salesmanToUpdate.setName(salesmanDTO.getName());
        salesmanToUpdate.setSurname(salesmanDTO.getSurname());

        userToUpdate.setUserName(salesmanDTO.getUserName());
        userToUpdate.setEmail(salesmanDTO.getEmail());

        if (salesmanDTO.getPassword() != null && !salesmanDTO.getPassword().isEmpty()) {
            String hashedPassword = bCryptPasswordEncoder.encode(salesmanDTO.getPassword());
            userToUpdate.setPassword(hashedPassword);
        }
        userJpaRepository.save(userToUpdate);
        salesmanJpaRepository.save(salesmanToUpdate);

        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);
        return "info/update_salesman_done";
    }

    @DeleteMapping(value = DELETE_SALESMAN)
    public String deleteSalesman(
            @RequestParam("codeNameSurname") String codeNameSurname, Model model
    ) {
        salesmanService.deleteSalesmanByCodeNameSurname(codeNameSurname);

        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);

        return "info/delete_salesman_done";
    }
}
