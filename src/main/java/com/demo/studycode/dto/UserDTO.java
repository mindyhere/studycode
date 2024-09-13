package com.demo.studycode.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO { // 회원가입 및 로그인에 사용되는 데이터 객체

    private Long idx;
    private String userid;
    private String passwd;
    private String name;
    private String email;
    private String phone;
    private String profile;
//    private String role;
    private String token;
    private int status;


    public UserDTO(String userid, String passwd) {
        this.userid = userid;
        this.passwd = passwd;
    }
}
