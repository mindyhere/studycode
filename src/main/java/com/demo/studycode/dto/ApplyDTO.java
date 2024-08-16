package com.demo.studycode.dto;

import com.demo.studycode.model.Study;
import com.demo.studycode.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApplyDTO {

    private long id;
    private Study study;
    private User user;
    private int status;

}
