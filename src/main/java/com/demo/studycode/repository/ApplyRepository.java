package com.demo.studycode.repository;

import com.demo.studycode.model.Apply;
import com.demo.studycode.model.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
//    List<Apply> findByStudyId(Long studyId);

    int countByStudyId(Long studyId);
}
