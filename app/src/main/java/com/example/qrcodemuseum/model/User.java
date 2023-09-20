package com.example.qrcodemuseum.model;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String user;
    private String email;
    private String phone;
    private String password;
    private Integer userType; //1 - Teacher, 2 - Student, 3 - Visitor, 4 - Adm

    private User() {

    }

    public User(Integer id, String user, String email, String phone, String password, Integer userType) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
