package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.ServiceDAO;
import com.codfish.bikeSalesAndService.domain.Service;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.ServiceJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.ServiceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ServiceRepository implements ServiceDAO {

    private final ServiceJpaRepository serviceJpaRepository;
    private final ServiceEntityMapper mapper;

    @Override
    public List<Service> findAll() {
        return serviceJpaRepository.findAll().stream()
                .map(mapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Service> findByServiceCode(String serviceCode) {
        return serviceJpaRepository.findByServiceCode(serviceCode)
                .map(mapper::mapFromEntity);
    }
}
