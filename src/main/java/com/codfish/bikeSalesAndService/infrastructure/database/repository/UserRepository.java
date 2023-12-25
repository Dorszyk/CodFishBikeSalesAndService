package com.codfish.bikeSalesAndService.infrastructure.database.repository;

import com.codfish.bikeSalesAndService.business.dao.UserDAO;
import com.codfish.bikeSalesAndService.domain.User;
import com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper.UserEntityMapper;
import com.codfish.bikeSalesAndService.infrastructure.security.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    @Override
    public List<User> findAvailable() {
        return userJpaRepository.findAll().stream()
                .map(userEntityMapper::mapFromEntity)
                .toList();
    }
}
