package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.Service;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceEntityMapper {

    @Mapping(target = "servicePersons", ignore = true)
    Service mapFromEntity(ServiceEntity entity);
}
