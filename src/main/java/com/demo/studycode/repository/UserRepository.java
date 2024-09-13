package com.demo.studycode.repository;

import com.demo.studycode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserid(String userid);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPhone(String email, String phone);

    Optional<User> findByUseridAndEmailAndPhone(String userid, String email, String phone);

}
