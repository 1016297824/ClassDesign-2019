package com.classdesign.classdesign.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mission {

    public static final String isModify = "可修改";
    public static final String notModify = "不可修改";

    public static final String isReplay = "已回复";
    public static final String notReplay = "未回复";
    public static final String notTime = "未按时回复";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String no;
    private String name;
    private String content;
    private String replay;
    private LocalDateTime endTime;
    private LocalDateTime completeTime;

    @ManyToOne
    private User user;

    private String modify = Mission.isModify;
    private String status = Mission.notReplay;
}
