package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.PartDTO;
import com.codfish.bikeSalesAndService.domain.Part;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartMapper {

    PartDTO map(Part part);

}
