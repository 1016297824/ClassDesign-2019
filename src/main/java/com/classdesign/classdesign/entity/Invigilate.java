package com.classdesign.classdesign.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.nio.channels.Pipe;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Invigilate {
    public static final String isDistribution = "已分配";
    public static final String done = "已完成";
    public static final String isReceive = "已接收";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String course;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToMany
    private List<User> user;
    private String status = "未分配";

    private String receive = "未接收";
}
