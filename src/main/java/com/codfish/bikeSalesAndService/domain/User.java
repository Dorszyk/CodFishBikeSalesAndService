package com.codfish.bikeSalesAndService.domain;

import com.codfish.bikeSalesAndService.infrastructure.security.RoleEntity;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "userId")
@ToString(of = {"userId", "userName", "email", "password"})
public class User {

    int userId;
    String userName;
    String email;
    String password;
    Boolean active;
    Set<RoleEntity> roles;
}
