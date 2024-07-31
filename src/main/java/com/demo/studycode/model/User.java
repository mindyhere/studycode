package com.demo.studycode.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @ColumnDefault("1")
    private int role;   // 일반유저 1, 관리자 0

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Auth auth;

    @Builder
    public User(Long id, String email, String passwd, int role) {
        this.email = email;
        this.passwd = passwd;
        this.role = role;
    }
}
