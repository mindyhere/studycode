package com.demo.studycode.dto;

import com.demo.studycode.model.User;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO { // 회원가입 및 로그인에 사용되는 데이터 객체
    private Long id;
    private String email;
    private String passwd;
    private String name;
    private String phone;
    private String profile;
    private int role;

    public UserDTO(Optional<User> user) {
        this.id = user.get().getId();
        this.email = user.get().getEmail();
        this.phone = user.get().getPhone();
        this.name = user.get().getName();
        this.profile = user.get().getProfile();
        this.role = user.get().getRole();
    }

    // DB생성 요청 시, DTO객체에서 Entity객체로 변환
    public User toEntity() {
        return User.builder()
                .role(this.role)
                .email(this.email)
                .passwd(this.passwd)
                .build();
    }

}
