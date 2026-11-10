package com.example.studentManagementSystem.repository;

import com.example.studentManagementSystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByUsername(String Username);
    Optional<Users> findByUsername(String Username);
}
