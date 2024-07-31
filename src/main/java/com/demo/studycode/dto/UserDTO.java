package com.demo.studycode.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO { // 회원가입 및 로그인에 사용되는 데이터 객체

    private Long id;
    private String email;
    private String passwd;
    private String name;
    private String phone;
    private String profile;
    private String role;
    private String token;

    public UserDTO(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }
}
