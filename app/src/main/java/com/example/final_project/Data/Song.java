package com.example.final_project.Data;

public class Song {
    private String name;
    private float length;

    public Song() { }

    public Song(String name,float length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public Song setName(String name) {
        this.name = name;
        return this;
    }

    public float getLength() {
        return length;
    }

    public Song setLength(float length) {
        this.length = length;
        return this;
    }
}
