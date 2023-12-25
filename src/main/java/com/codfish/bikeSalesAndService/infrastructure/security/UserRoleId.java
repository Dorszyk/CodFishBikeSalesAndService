package com.codfish.bikeSalesAndService.infrastructure.security;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "userId")
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {
    private Integer userId;
    private Integer roleId;

}
