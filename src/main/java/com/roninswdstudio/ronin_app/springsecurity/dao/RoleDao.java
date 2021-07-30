package com.roninswdstudio.ronin_app.springsecurity.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roninswdstudio.ronin_app.springsecurity.entity.ERole;
import com.roninswdstudio.ronin_app.springsecurity.entity.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}