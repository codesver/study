package codesver.springboot.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    // Private information
    @Column(name = "name")
    private String name;
    private int age;
    private boolean gender;

    // Account information
    private String nick;
    private String id;
    private String pw;
}
