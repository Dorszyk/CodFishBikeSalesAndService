package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.PartDAO;
import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PartJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PartCatalogService {


    private final PartDAO partDAO;
    private final PartJpaRepository partJpaRepository;

    @Transactional
    public List<Part> findAllByParts(List<String> partSerialNumbers) {
        List<Part> parts = partDAO.findAllBySerialNumbers(partSerialNumbers);
        List<String> filteredSerialNumbers = partSerialNumbers.stream()
                .filter(serialNumber -> !serialNumber.equals("NONE"))
                .toList();
        if (filteredSerialNumbers.isEmpty()) {
            return new ArrayList<>();
        }
        if (parts.isEmpty()) {
            throw new NotFoundException("Could not find parts by part serial numbers: " + partSerialNumbers);
        }
        return parts;
    }

    public List<Part> findAll() {
        List<Part> parts = partDAO.findAll();
        log.info("Available parts: [{}]", parts.size());
        return parts;
    }

    @Transactional
    public void deletePart(String serialNumber) {
        Optional<PartEntity> optionalPartEntity = partJpaRepository.findBySerialNumber(serialNumber);
        if (optionalPartEntity.isEmpty()) {
            log.error("Cannot delete part. No part found with serial number: {}", serialNumber);
            throw new NotFoundException(String.format("No part found with serial number: %s", serialNumber));
        }
        partJpaRepository.delete(optionalPartEntity.get());
    }
}