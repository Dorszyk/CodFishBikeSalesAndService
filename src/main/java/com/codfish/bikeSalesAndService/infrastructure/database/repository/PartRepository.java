package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.PartDAO;
import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PartJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.PartEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PartRepository implements PartDAO {
    private final PartJpaRepository partJpaRepository;
    private final PartEntityMapper mapper;

    @Override
    public List<Part> findAll() {
        return partJpaRepository.findAll().stream()
                .map(mapper::mapFromEntity)
                .toList();
    }
    @Override
    public Optional<Part> finBySerialNumber(String serialNumber) {
        return partJpaRepository.findBySerialNumber(serialNumber)
                .map(mapper::mapFromEntity);
    }
    @Override
    public List<Part> findAllBySerialNumbers(List<String> serialNumbers) {
        return partJpaRepository.findAllBySerialNumberIn(serialNumbers).stream()
                .map(mapper::mapFromEntity)
                .collect(Collectors.toList());
    }
}
