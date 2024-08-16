package com.demo.studycode.dto;

import com.demo.studycode.model.Study;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachDTO {
    private Long id;
    private String filename;
    private Study study;
}
