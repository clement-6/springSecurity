package com.JWT.springSecurity.repository;

import com.JWT.springSecurity.model.Role;
import com.JWT.springSecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);
    Users findByRole(Role role);
}
