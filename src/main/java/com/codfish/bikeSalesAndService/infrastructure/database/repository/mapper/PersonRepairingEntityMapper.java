package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.PersonRepairing;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.PersonRepairingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonRepairingEntityMapper {

    @Mapping(target = "servicePerson", ignore = true)
    PersonRepairing mapFromEntity(PersonRepairingEntity entity);
}
