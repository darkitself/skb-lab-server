package com.test.skblabserver.database;

import com.test.skblabserver.registration.UserData;

import javax.persistence.*;

@Entity
@Table(name = "users", indexes = {@Index(columnList = "login, email, phone_number")})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true, name = "phone_number")
    private String phoneNumber;

    public UserEntity() {

    }

    public UserEntity(UserData userData) {
        this.login = userData.getLogin();
        this.password = userData.getPassword();
        this.email = userData.getEmail();
        this.phoneNumber = userData.getPhoneNumber();
    }
}
