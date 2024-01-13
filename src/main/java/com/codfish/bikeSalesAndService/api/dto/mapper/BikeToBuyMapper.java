package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.BikeToBuyDTO;
import com.codfish.bikeSalesAndService.domain.BikeToBuy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BikeToBuyMapper {

    @Mapping(source = "category", target = "category")
    @Mapping(source = "subcategory", target = "subcategory")
    @Mapping(source = "serial", target = "serial")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "model", target = "model")
    @Mapping(source = "year", target = "year")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "price", target = "price")
    BikeToBuyDTO map(final BikeToBuy bikeToBuy);


}
