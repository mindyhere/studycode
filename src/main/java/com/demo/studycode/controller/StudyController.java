package com.demo.studycode.controller;

import com.demo.studycode.dto.AttachDTO;
import com.demo.studycode.dto.StudyDTO;
import com.demo.studycode.model.Attach;
import com.demo.studycode.model.Study;
import com.demo.studycode.model.User;
import com.demo.studycode.repository.ApplyRepository;
import com.demo.studycode.repository.AttachRepository;
import com.demo.studycode.repository.StudyRepository;
import com.demo.studycode.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/study/*")
public class StudyController {
    @Autowired
    StudyRepository studyRepository;

    @Autowired
    AttachRepository attachRepository;

    @Autowired
    ApplyRepository applyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("insert")
    public void insert(StudyDTO dto, HttpServletRequest request) {
        Study s = modelMapper.map(dto, Study.class);
        s.setUser(new User(dto.getId()));
        Study study = studyRepository.save(s);
        long id = study.getId();
        String[] files = dto.getFiles();
        if (files != null && files.length > 0) {
            try {
                for (String filename : files) {
                    Attach attach = new Attach();
                    attach.setFilename(filename);
                    attach.setStudy(new Study(id));
                    attachRepository.save(attach);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
