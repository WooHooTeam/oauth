package com.security.oauth.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="T_USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name", length = 20, unique = true, nullable = false)
    private String username;
    @Column(length = 400, nullable = false)
    private String password;
    @Column(name = "user_type", nullable = false)
    private int userType;
    @Column(nullable = false)
    private Date date;
}