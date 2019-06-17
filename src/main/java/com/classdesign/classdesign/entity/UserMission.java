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
public class UserMission {

    public static final String isReplay = "已回复";
    public static final String notReplay = "未回复";
    public static final String notTime = "未按时完成";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Mission mission;

    private String replay;
    private LocalDateTime completeTime;

    private String status = UserMission.notReplay;
}
