package com.demo.studycode.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User { // 유저 정보 저장 용도

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    @Column(length = 50, unique = true)
    private String userid;

    @NotNull
    @Column(length = 100)
    private String passwd;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(length = 13)
    private String phone;

    @Column
    private String profile;

    @Column
    private String token;

    @ColumnDefault("1")
    @Column
    private int status;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    List<Board> boardList = new ArrayList<>();

    @Builder
    public User(Long idx) {
        this.idx = idx;
//        this.email = email;
//        this.name = name;
//        this.role = role;
    }

}
