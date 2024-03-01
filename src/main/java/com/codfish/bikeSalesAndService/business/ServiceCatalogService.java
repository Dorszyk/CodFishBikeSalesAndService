package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.ServiceDAO;
import com.codfish.bikeSalesAndService.domain.Service;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceCatalogService {

    private final ServiceDAO serviceDAO;

    @Transactional
    public Service findService(String serviceCode){
        Optional<Service> service = serviceDAO.findByServiceCode(serviceCode);
        if (service.isEmpty()){
            throw new NotFoundException("Could not find service by service code: [%s]".formatted(service));
        }
        return service.get();
    }

    public List<Service> findAll(){
        List<Service>services =serviceDAO.findAll();
        log.info("Available services: [{}]", services.size());
        return services;
    }
}
