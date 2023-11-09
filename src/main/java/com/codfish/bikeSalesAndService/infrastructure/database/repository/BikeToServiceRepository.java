package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.BikeToServiceDAO;
import com.codfish.bikeSalesAndService.domain.BikeHistory;
import com.codfish.bikeSalesAndService.domain.BikeToService;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToServiceEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.BikeToServiceJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.BikeToServiceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BikeToServiceRepository implements BikeToServiceDAO {

    private final BikeToServiceJpaRepository bikeToServiceJpaRepository;
    private final BikeToServiceEntityMapper bikeToServiceEntityMapper;

    @Override
    public List<BikeToService> findAll() {
        return bikeToServiceJpaRepository.findAll().stream()
                .map(bikeToServiceEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<BikeToService> findBikeToServiceBySerial(String bikeSerial) {
        return bikeToServiceJpaRepository.findOptionalBySerial(bikeSerial)
                .map(bikeToServiceEntityMapper::mapFromEntity);
    }

    @Override
    public BikeToService saveBikeToService(BikeToService bike) {
        BikeToServiceEntity toSave = bikeToServiceEntityMapper.mapToEntity(bike);
        BikeToServiceEntity saved = bikeToServiceJpaRepository.save(toSave);
        return bikeToServiceEntityMapper.mapFromEntity(saved);
    }

    @Override
    public BikeHistory findBikeHistoryBySerial(String bikeSerial) {
        BikeToServiceEntity entity = bikeToServiceJpaRepository.findBySerial(bikeSerial);
        return bikeToServiceEntityMapper.mapFromEntity(bikeSerial, entity);
    }
}
