package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartEntityMapper {

    @Mapping(target = "serviceParts",ignore = true)
    Part mapFromEntity(PartEntity entity);

}
