package com.example.final_project.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Kindergarten implements Cloneable{

    //private String key;
    private String name;
    private String kindergartenTeacher;
    private String updatesForToday;
    private ArrayList<String> songList = new ArrayList<>();
    private Map<String,String> eventMap = new HashMap<String, String>();



    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Kindergarten() { }

    public Kindergarten(/*String key,*/ String name, String kindergartenTeacher, String updatesForToday,
                        ArrayList<String> songList, Map<String,String> eventMap) {
        //this.key = key;
        this.name = name;
        this.kindergartenTeacher = kindergartenTeacher;
        this.updatesForToday = updatesForToday;
        this.songList = songList;
        this.eventMap = eventMap;
    }

    /*public String getKey() {
        return key;
    }

    public Kindergarten setKey(String key) {
        this.key = key;
        return this;
    }*/

    public String getName() {
        return name;
    }

    public Kindergarten setName(String name) {
        this.name = name;
        return this;
    }

    public String getKindergartenTeacher() {
        return kindergartenTeacher;
    }

    public Kindergarten setKindergartenTeacher(String kindergartenTeacher) {
        this.kindergartenTeacher = kindergartenTeacher;
        return this;
    }

    public String getUpdatesForToday() {
        return updatesForToday;
    }

    public Kindergarten setUpdatesForToday(String updatesForToday) {
        this.updatesForToday = updatesForToday;
        return this;
    }

    public ArrayList<String> getSongList() {
        return songList;
    }

    public Kindergarten setSongList(ArrayList<String> songList) {
        this.songList = songList;
        return this;
    }

    public Map<String, String> getEventMap() {
        return eventMap;
    }

    public Kindergarten setEventMap(Map<String, String> eventMap) {
        this.eventMap = eventMap;
        return this;
    }
}
