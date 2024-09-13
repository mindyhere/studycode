package com.demo.studycode.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_board")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Lob
    @NotNull
    @Column
    private String content;

    @Column
    private String tag1;

    @Column
    private String tag2;

    @Column
    private String tag3;

    @Column
    private String tag4;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user; // 작성자 PK

    @NotNull
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime uploadedAt;

    @NotNull
    @ColumnDefault("0")
    @Column
    private int view; // 조회수

    @OneToMany(mappedBy = "board")
    List<Attach> attachList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    List<Attach> likeCounts = new ArrayList<>();

    public Board(long idx) {
        this.idx = idx;
    }
}
