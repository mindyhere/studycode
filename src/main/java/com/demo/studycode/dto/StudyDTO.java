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
public class StudyDTO {

    private Long id;
    private String title;
    private String description;
    private String topic;
    private User user;
    private int maxParticipants;
    private Date begin;
    private Date end;
    private LocalDateTime createdAt;
    private int status;
    private int count;
    private String[] files;

}
