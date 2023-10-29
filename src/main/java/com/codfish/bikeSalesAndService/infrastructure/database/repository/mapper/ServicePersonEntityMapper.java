package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.ServicePerson;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicePersonEntityMapper {

    ServicePersonEntity mapToEntity(ServicePerson servicePerson);
}
