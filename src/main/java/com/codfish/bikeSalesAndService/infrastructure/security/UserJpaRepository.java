package com.codfish.bikeSalesAndService.infrastructure.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserName(String userName);
    @Query("SELECT GREATEST(MAX(s.userId), MAX(p.userId)) FROM SalesmanEntity s, PersonRepairingEntity p")
    Optional<Integer> findMaxUserId();
}
