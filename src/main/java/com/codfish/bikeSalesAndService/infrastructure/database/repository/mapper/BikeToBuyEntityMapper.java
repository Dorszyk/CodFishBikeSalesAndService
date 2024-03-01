package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;

import com.codfish.bikeSalesAndService.domain.BikeToBuy;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeToBuyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BikeToBuyEntityMapper {

    @Mapping(target = "invoice", ignore = true)
    BikeToBuy mapFormEntity(BikeToBuyEntity entity);

    @Mapping(target = "invoice", ignore = true)
    BikeToBuyEntity mapToEntity(BikeToBuy bike);
}
