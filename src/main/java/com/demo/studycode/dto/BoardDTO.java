package com.demo.studycode.dto;


import com.demo.studycode.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class BoardDTO {

    private Long idx;
    private String content;
    private String tag1;
    private String tag2;
    private String tag3;
    private String tag4;
    private User user;
    private LocalDateTime uploadedAt;
    private int view;
    private String[] files;

}
