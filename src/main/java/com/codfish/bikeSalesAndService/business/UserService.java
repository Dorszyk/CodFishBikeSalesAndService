package com.codfish.bikeSalesAndService.business;


import com.codfish.bikeSalesAndService.business.dao.UserDAO;
import com.codfish.bikeSalesAndService.domain.User;
import com.codfish.bikeSalesAndService.infrastructure.security.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final RoleRepository roleJpaRepository;
    private final UserRoleJpaRepository userRoleJpaRepository;
    private final UserDAO userDAO;

    @Transactional
    public void addSalesman(String userName, String email, String password) {

        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(true);
        user = userJpaRepository.save(user);

        RoleEntity roleSalesman = roleJpaRepository.findByRole("SALESMAN");
        RoleEntity rolePersonRepairing = roleJpaRepository.findByRole("PERSON_REPAIRING");

        UserRoleEntity userRoleSalesman = new UserRoleEntity();
        userRoleSalesman.setUserId(user.getUserId());
        userRoleSalesman.setRoleId(roleSalesman.getRoleId());
        userRoleJpaRepository.save(userRoleSalesman);

        UserRoleEntity userRolePersonRepairing = new UserRoleEntity();
        userRolePersonRepairing.setUserId(user.getUserId());
        userRolePersonRepairing.setRoleId(rolePersonRepairing.getRoleId());
        userRoleJpaRepository.save(userRolePersonRepairing);
    }

    @Transactional
    public List<User> findAllUsers() {
        List<User> availableUser = userDAO.findAvailable();
        log.info("Available User: [{}]", availableUser.size());
        return availableUser;
    }

}
