package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.domain.User;

import java.util.List;

public interface UserDAO {
    List<User> findAvailable();
}
