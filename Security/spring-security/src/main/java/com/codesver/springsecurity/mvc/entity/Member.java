package com.codesver.springsecurity.mvc.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private Long memberId;
    private String name;
    private String userId;
    private String userPw;
    private Role role;

    @Builder
    public Member(String name, String userId, String userPw, Role role) {
        this.name = name;
        this.userId = userId;
        this.userPw = userPw;
        this.role = role;
    }
}
