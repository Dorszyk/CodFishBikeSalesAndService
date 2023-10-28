package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.PersonRepairingDTO;
import com.codfish.bikeSalesAndService.domain.PersonRepairing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonRepairingMapper {
    PersonRepairingDTO map(final PersonRepairing personRepairing);
}
