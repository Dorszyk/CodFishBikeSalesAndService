package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.ServiceRequestProcessingDAO;
import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import com.codfish.bikeSalesAndService.domain.ServicePart;
import com.codfish.bikeSalesAndService.domain.ServicePerson;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePersonEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.BikeServiceRequestJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.PartJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.ServicePartJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.jpa.ServicePersonJpaRepository;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.ServicePartEntityMapper;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.ServicePersonEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
@AllArgsConstructor
public class ServiceRequestProcessingRepository implements ServiceRequestProcessingDAO {

    private final ServicePersonJpaRepository servicePersonJpaRepository;
    private final BikeServiceRequestJpaRepository bikeServiceRequestJpaRepository;
    private final PartJpaRepository partJpaRepository;
    private final ServicePartJpaRepository servicePartJpaRepository;
    private final ServicePersonEntityMapper servicePersonEntityMapper;
    private final ServicePartEntityMapper servicePartEntityMapper;

    @Override
    @Transactional
    public void process(
            BikeServiceRequest serviceRequest,
            ServicePerson servicePerson
    ) {
        ServicePersonEntity servicePersonEntity = servicePersonEntityMapper.mapToEntity(servicePerson);
        servicePersonJpaRepository.saveAndFlush(servicePersonEntity);
        if(Objects.nonNull(serviceRequest.getCompletedDateTime())){
            BikeServiceRequestEntity bikeServiceRequestEntity = bikeServiceRequestJpaRepository
                    .findById(serviceRequest.getBikeServiceRequestId())
                    .orElseThrow();
            bikeServiceRequestEntity.setCompletedDateTime(serviceRequest.getCompletedDateTime());
            bikeServiceRequestJpaRepository.saveAndFlush(bikeServiceRequestEntity);
        }
    }

    @Override
    @Transactional
    public void process(
            BikeServiceRequest serviceRequest,
            ServicePerson servicePerson,
            ServicePart servicePart
    ) {
        PartEntity partEntity = partJpaRepository.findById(servicePart.getPart().getPartId()).orElseThrow();
        ServicePartEntity servicePartEntity = servicePartEntityMapper.mapToEntity(servicePart);
        servicePartEntity.setPart(partEntity);
        servicePartJpaRepository.saveAndFlush(servicePartEntity);
        process(serviceRequest,servicePerson);

    }
}
