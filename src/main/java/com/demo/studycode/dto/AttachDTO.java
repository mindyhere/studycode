package com.demo.studycode.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class AttachDTO {
    private Long idx;
    private Long boardIdx;
    private String filename;
    private MultipartFile file;
}
