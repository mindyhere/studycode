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
@Table(name = "tb_study")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study { // 스터디 정보 저장

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String title;

    @Lob
    @NotNull
    @Column
    private String description;

    @NotNull
    @Column
    private String topic;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 작성자 PK

    @NotNull
    @Column
    private int maxParticipants;

    @Column
    private Date begin;

    @Column
    private Date end;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @NotNull
    @ColumnDefault("0")
    @Column
    private int status; // 마감여부(0:신청, 1:승인, -1:신청취소)

    @NotNull
    @ColumnDefault("0")
    @Column
    private int count; // 조회수

    @OneToMany(mappedBy = "study")
    List<Attach> attachList = new ArrayList<>();

    @OneToMany(mappedBy = "study")
    List<Apply> applicants = new ArrayList<>();

}
