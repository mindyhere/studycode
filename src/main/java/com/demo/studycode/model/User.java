package com.demo.studycode.model;

import com.demo.studycode.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User { // 유저 정보 저장 용도

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, unique = true)
    private String email;

    @NotNull
    @Column(length = 100)
    private String passwd;

    @Column(length = 50)
    private String name;

    @Column
    private String phone;

    @Column
    private String profile;

    @Column
    @ColumnDefault("USER")
    private String role; // USER, ADMIN

    @Builder
    public User(Long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    @Builder
    public User(UserDTO dto) {
        this.id = dto.getId();
        this.email = dto.getEmail();
        this.passwd = dto.getPasswd();
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.profile = dto.getProfile();
        this.role = dto.getRole();
    }
}
