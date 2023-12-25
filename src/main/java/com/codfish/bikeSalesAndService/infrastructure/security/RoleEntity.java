package com.codfish.bikeSalesAndService.infrastructure.security;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@EqualsAndHashCode(of = "roleId")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(of = {"roleId", "role"})
@Table(name = "codfish_bike_role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role")
    private String role;

}
