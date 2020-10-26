package com.example.final_project.Data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBase {

    private FirebaseAuth myAuth;
    private static FirebaseDatabase database;
    private DatabaseReference myRefUsers;

    private DatabaseReference myRefKindergarten;

    public FireBase setMyRefKindergartenPath(String path) {
        this.myRefKindergarten = myRefKindergarten;
        return this;
    }

    public DatabaseReference getMyRefKindergarten() {
        return myRefKindergarten;
    }

    public static FirebaseDatabase getInstance(){
        database = FirebaseDatabase.getInstance();
        return database;
    }
    public FirebaseAuth getMyAuth() {
        return myAuth;
    }

    public FireBase setMyAuth(FirebaseAuth myAuth) {
        this.myAuth = myAuth;
        return this;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public FireBase setDatabase(FirebaseDatabase database) {
        this.database = database;
        return this;
    }

    public DatabaseReference getMyRefUsers() {
        return myRefUsers;
    }

    public FireBase setMyRefUsers(DatabaseReference myRefUsers) {
        this.myRefUsers = myRefUsers;
        return this;
    }

    public FirebaseDatabase setDatabase(){
        database = FirebaseDatabase.getInstance();
        return database;
    }

}
