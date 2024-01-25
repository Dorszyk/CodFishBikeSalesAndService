package com.codfish.bikeSalesAndService.infrastructure.security;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "userId")
@Entity
@Table(name = "codfish_bike_user_role")
@IdClass(UserRoleId.class)
public class UserRoleEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Id
    @Column(name = "role_id")
    private Integer roleId;

}
