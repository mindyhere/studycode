package com.demo.studycode.repository;

import com.demo.studycode.model.Auth;
import com.demo.studycode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean findByUser(Optional<User> user);
}
