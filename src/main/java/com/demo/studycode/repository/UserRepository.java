package com.demo.studycode.repository;

import com.demo.studycode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
//    Optional<User> findById(long id);
//    Optional<User> findByEmailAndPwd(String email, String passwd);
}
