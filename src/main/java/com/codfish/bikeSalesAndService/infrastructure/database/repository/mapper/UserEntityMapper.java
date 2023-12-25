package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.User;
import com.codfish.bikeSalesAndService.infrastructure.security.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    @Mapping(target = "roles",ignore = true)
    User mapFromEntity(UserEntity entity);
}

