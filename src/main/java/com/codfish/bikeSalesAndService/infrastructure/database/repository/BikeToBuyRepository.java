package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.BikeToBuyDAO;
import com.codfish.bikeSalesAndService.domain.BikeToBuy;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.BikeToBuyJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.BikeToBuyEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BikeToBuyRepository implements BikeToBuyDAO {

    private final BikeToBuyJpaRepository bikeToBuyJpaRepository;
    private final BikeToBuyEntityMapper bikeToBuyEntityMapper;

    @Override
    public List<BikeToBuy> findAvailable() {
        return bikeToBuyJpaRepository.findAvailableBike().stream()
                .map(bikeToBuyEntityMapper::mapFormEntity)
                .toList();
    }

    @Override
    public Optional<BikeToBuy> findBikeToBuyBySerial(String bikeSerial) {
        return bikeToBuyJpaRepository.findBySerial(bikeSerial)
                .map(bikeToBuyEntityMapper::mapFormEntity);
    }
}
