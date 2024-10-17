package com.example.myfinance.repositories;

import com.example.myfinance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(UUID userId);

    User findByEmail(String email);
}
