package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.ServicePart;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicePartEntityMapper {

    ServicePartEntity mapToEntity(ServicePart servicePart);
}
