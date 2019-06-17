package com.classdesign.classdesign.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mission {

    public static final String isModify = "可修改";
    public static final String notModify = "不可修改";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String no;
    private String name;
    private String content;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "mission", fetch = FetchType.LAZY)
    private List<UserMission> userMissions;

    private String modify = Mission.isModify;
}
