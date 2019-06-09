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

    public static final String Super_Manager = "5df48d9f4d5g7g8f4g5fg48r7g68";
    public static final String Manager = "1f5g4f8g41sf98d7fe";
    public static final String Teacher = "54e8fd5f4s68e784jy17s8dg4ht8";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String no;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private String intro;
    private String mobile;
    private String authority;

    public User(String no,String password,String name,String intro,String mobile,String authority) {
        this.no=no;
        this.password=password;
        this.name = name;
        this.intro=intro;
        this.mobile=mobile;
        this.authority=authority;
    }
}