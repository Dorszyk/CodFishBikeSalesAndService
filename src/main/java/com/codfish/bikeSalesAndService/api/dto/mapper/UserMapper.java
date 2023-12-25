package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.UserDTO;
import com.codfish.bikeSalesAndService.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO map(final User user);
}
