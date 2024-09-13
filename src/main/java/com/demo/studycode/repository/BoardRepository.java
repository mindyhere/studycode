package com.demo.studycode.repository;

import com.demo.studycode.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends PagingAndSortingRepository<Board, Long> {

    @Query(value =
            "select * " +
              "from tb_board b " +
              "join tb_user u " +
                "on b.user_idx=u.idx " +
             "where b.tag1 like %:keyword% " +
                "or b.tag2 like %:keyword% " +
                "or b.tag3 like %:keyword% " +
                "or b.tag4 like %:keyword% " +
                "or b.content like %:keyword% " +
                "or u.name like %:keyword%"
            , nativeQuery = true)
    Page<Board> findByAll(@Param("keyword") String keyword, Pageable pageable);

    @Query(value =
            "select * " +
              "from tb_board b " +
              "join tb_user u " +
                "on b.user_idx=u.idx " +
             "where u.name like %:keyword%"
            , nativeQuery = true)
    Page<Board> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<Board> findByTag1Containing(String keyword, Pageable pageable);

    Board save(Board board);

    Optional<Board> findById(Long idx);

    void deleteByIdx(Long idx);
}
