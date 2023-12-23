package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.api.dto.SalesmanDTO;
import com.codfish.bikeSalesAndService.api.dto.mapper.SalesmanMapper;
import com.codfish.bikeSalesAndService.business.BikePurchaseService;
import com.codfish.bikeSalesAndService.business.SalesmanService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.SalesmanEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.SalesmanJpaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AddUpdateSalesmanController {
    private static final String ADD_UPDATE_SALESMAN = "/add_update_salesman";
    private static final String ADD_SALESMAN = "/add_salesman";
    private static final String UPDATE_SALESMAN = "/update_salesman";
    private static final String DELETE_SALESMAN = "/delete_salesman";

    private final BikePurchaseService bikePurchaseService;
    private final SalesmanMapper salesmanMapper;
    private final SalesmanJpaRepository salesmanJpaRepository;
    private final SalesmanService salesmanService;


    @GetMapping(value = ADD_UPDATE_SALESMAN)
    public String salesmanAddUpdatePage(Model model) {
        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
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

        Integer nextUserId = salesmanJpaRepository.findMaxUserId().orElse(0) + 1;

        SalesmanEntity newSalesman = SalesmanEntity.builder()
                .name(salesmanDTO.getName())
                .surname(salesmanDTO.getSurname())
                .codeNameSurname(salesmanDTO.getCodeNameSurname())
                .userId(nextUserId)
                .build();
        salesmanJpaRepository.save(newSalesman);


        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);

        return "info/add_salesman_done";
    }

    @RequestMapping(value = UPDATE_SALESMAN)
    public String updateSalesman(
            @Valid @ModelAttribute("salesmanDTO") SalesmanDTO salesmanDTO, Model model
    ) {
        SalesmanEntity salesmanToUpdate = salesmanJpaRepository.findByCodeNameSurname(salesmanDTO.getCodeNameSurname())
                .orElseThrow(() -> new NotFoundException(
                        "Salesman not found, CodeNameSurname: [%s]".formatted(salesmanDTO.getCodeNameSurname())));
        salesmanToUpdate.setCodeNameSurname(salesmanDTO.getCodeNameSurname());
        salesmanToUpdate.setName(salesmanDTO.getName());
        salesmanToUpdate.setSurname(salesmanDTO.getSurname());
        salesmanJpaRepository.save(salesmanToUpdate);

        var availableSalesmen = bikePurchaseService.availableSalesmen().stream()
                .map(salesmanMapper::map)
                .toList();
        model.addAttribute("availableSalesmenDTOs", availableSalesmen);
        return "info/update_salesman_done";
    }

    @PostMapping(value = DELETE_SALESMAN)
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
