package com.demo.studycode.repository;

import com.demo.studycode.model.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    List<Apply> findByStudyId(Long study_id);

    int countByStudyId(Long studyId);
}
