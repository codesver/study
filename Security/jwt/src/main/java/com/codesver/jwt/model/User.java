package com.codesver.jwt.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String roles;   // USER, ADMIN

    public List<String> getRoles() {
        return roles.isEmpty() ? new ArrayList<>() : Arrays.asList(roles.split(","));
    }
}
