package com.example.myapplication01.models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private String key;
    private Map<String, Member> members = new ConcurrentHashMap<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Member> members) {
        this.members = members;
    }
}
