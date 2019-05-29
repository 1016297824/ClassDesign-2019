package com.classdesign.classdesign.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    public static final int Super_Manager = 1;
    public static final int Manager = 2;
    public static final int Teacher = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String no;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private String post;
    private String intro;
    private String mobile;
    private int authority;

    public User(String no,String password,String name,String post,String intro,String mobile,int authority) {
        this.no=no;
        this.password=password;
        this.name = name;
        this.post=post;
        this.intro=intro;
        this.mobile=mobile;
        this.authority=authority;
    }
}