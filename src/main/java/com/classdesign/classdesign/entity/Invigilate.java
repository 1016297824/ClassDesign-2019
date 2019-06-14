package com.classdesign.classdesign.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Invigilate {

    public static final String noDistribution = "未分配";
    public static final String isDistribution = "已分配";
    public static final String done = "已完成";

    public static final String notReceive = "未接收";
    public static final String isReceive = "已接收";

    public static final String notSend = "未发送";
    public static final String isSend = "已发送";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String no;
    private String course;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "invigilate", fetch = FetchType.LAZY)
    private List<UserInvigilate> user;

    private String status = noDistribution;
    private String receive = notReceive;
    private String send = notSend;

}
