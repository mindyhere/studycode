package com.demo.studycode.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    String UPLOAD_PATH = "/Users/9suaveee/miindy/work/studycode/src/main/resources/static/images/";
    String fileName = "-";

    public String uploadProfile(MultipartFile img) throws IOException {
        fileName = img.getOriginalFilename();
        img.transferTo(new File(UPLOAD_PATH + fileName));
        return fileName;
    }
}
