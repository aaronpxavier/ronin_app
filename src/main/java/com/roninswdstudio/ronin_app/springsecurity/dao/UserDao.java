package com.roninswdstudio.ronin_app.springsecurity.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roninswdstudio.ronin_app.springsecurity.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);

    Boolean existsByEmail(String email);


}