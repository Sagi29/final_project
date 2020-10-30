package com.example.final_project.Data;

import java.util.UUID;

public class User {

    private String uid;
    private String name;
    private String email;
    private String childName;
    private String kindergartenName;
    //private boolean isAdmin;
    private String password;
    private int isAdmin; // 1=Admin  0=Not Admin
    private String personalFile;


    public User() {this.uid = UUID.randomUUID().toString(); }



    public User(String uid, String name, String email, String childName, String kindergartenName, int isAdmin, String password,String personalFile) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.childName = childName;
        this.kindergartenName = kindergartenName;
        this.isAdmin = isAdmin;
        this.password = password;
        this.personalFile = personalFile;
    }



    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getChildName() {
        return childName;
    }

    public User setChildName(String childName) {
        this.childName = childName;
        return this;
    }

    public String getKindergartenName() {
        return kindergartenName;
    }

    public User setKindergartenName(String kindergartenName) {
        this.kindergartenName = kindergartenName;
        return this;
    }

    public int getAdmin() {
        return isAdmin;
    }

    public User setAdmin(int admin) {
        isAdmin = admin;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getPersonalFile() {
        return personalFile;
    }

    public User setPersonalFile(String personalFile) {
        this.personalFile = personalFile;
        return this;
    }
}
