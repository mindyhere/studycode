package com.demo.studycode.repository;

import com.demo.studycode.model.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<Attach, Long> {
    void deleteById(Long id);

    int countByStudyId(int study_id);
}
