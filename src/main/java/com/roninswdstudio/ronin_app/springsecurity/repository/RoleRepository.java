package com.roninswdstudio.ronin_app.springsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roninswdstudio.ronin_app.springsecurity.models.ERole;
import com.roninswdstudio.ronin_app.springsecurity.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}