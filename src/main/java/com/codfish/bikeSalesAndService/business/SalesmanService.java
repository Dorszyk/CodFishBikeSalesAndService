package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.SalesmanDAO;
import com.codfish.bikeSalesAndService.domain.Salesman;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SalesmanService {


    private final SalesmanDAO salesmanDAO;

    @Transactional
    public List<Salesman> findAvailable() {
        List<Salesman> availableSalesmen = salesmanDAO.findAvailable();
        log.info("Available salesmen: [{}]", availableSalesmen.size());
        return availableSalesmen;
    }

    @Transactional
    public Salesman findSalesman(String codeNameSurname) {
        Optional<Salesman> salesman = salesmanDAO.findByCodeNameSurname(codeNameSurname);
        if (salesman.isEmpty()) {
            throw new NotFoundException("Could not find salesman by codeNameSurname: [%s".formatted(codeNameSurname));
        }
        return salesman.get();
    }
}
