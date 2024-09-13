package com.demo.studycode.repository;

import com.demo.studycode.model.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<Attach, Long> {
    void deleteByIdx(Long idx);

    int countByBoardIdx(int board_idx);
}
