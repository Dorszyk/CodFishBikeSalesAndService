package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.SalesmanDAO;
import com.codfish.bikeSalesAndService.domain.Salesman;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.SalesmanJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.SalesmanEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SalesmanRepository implements SalesmanDAO {

    private final SalesmanJpaRepository salesmanJpaRepository;
    private final SalesmanEntityMapper salesmanEntityMapper;

    @Override
    public List<Salesman> findAvailable() {
        return salesmanJpaRepository.findAll().stream()
                .map(salesmanEntityMapper::mapFromEntity)
                .toList();
    }
    @Override
    public Optional<Salesman> findByCodeNameSurname(String codeNameSurname) {
        return salesmanJpaRepository.findByCodeNameSurname(codeNameSurname)
                .map(salesmanEntityMapper::mapFromEntity);
    }
}
