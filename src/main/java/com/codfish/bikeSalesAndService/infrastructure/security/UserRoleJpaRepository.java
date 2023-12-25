package com.codfish.bikeSalesAndService.infrastructure.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, UserRoleId> {

}
