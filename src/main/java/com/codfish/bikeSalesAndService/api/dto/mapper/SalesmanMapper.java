package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.SalesmanDTO;
import com.codfish.bikeSalesAndService.domain.Salesman;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesmanMapper {
    SalesmanDTO map(final Salesman salesman);

}
