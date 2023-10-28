package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.ServiceDTO;
import com.codfish.bikeSalesAndService.domain.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO map(Service service);

}
