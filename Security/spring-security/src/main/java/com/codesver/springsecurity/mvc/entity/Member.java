package com.codesver.springsecurity.mvc.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private Long memberId;
    private String name;
    private String userId;
    private String userPw;
    private Role role;
}
