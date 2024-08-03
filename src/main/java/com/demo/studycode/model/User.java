package com.demo.studycode.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
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

    @Column(columnDefinition = "varchar(50) default 'USER'")
    private String role; // USER, ADMIN

    @Column
    private String token;

    @Builder
    public User(Long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

}
