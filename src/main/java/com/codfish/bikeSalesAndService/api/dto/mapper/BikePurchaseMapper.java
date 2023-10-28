package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.BikePurchaseDTO;
import com.codfish.bikeSalesAndService.domain.BikePurchaseRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BikePurchaseMapper {
    BikePurchaseRequest map (final BikePurchaseDTO dto);
}
