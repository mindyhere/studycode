package com.demo.studycode.repository;

import com.demo.studycode.model.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyRepository extends PagingAndSortingRepository<Study, Integer> {

    @Query(value =
            "select * " +
              "from tb_study s " +
              "join tb_user u " +
                "on s.user_id=u.id " +
             "where s.title like %:keyword% " +
                "or s.topic like %:keyword% " +
                "or s.description like %:keyword% " +
                "or u.name like %:keyword%"
            , nativeQuery = true)
    Page<Study> findByAll(@Param("keyword") String keyword, Pageable pageable);

    @Query(value =
            "select * " +
              "from tb_study s " +
              "join tb_user u " +
                "on s.user_id=u.id " +
             "where u.name like %:keyword%"
            , nativeQuery = true)
    Page<Study> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<Study> findByTitleContaining(String title, Pageable pageable);

    Page<Study> findByDescriptionContaining(String description, Pageable pageable);

    Study save(Study study);

    Optional<Study> findById(Long id);

    void deleteById(Long id);
}
