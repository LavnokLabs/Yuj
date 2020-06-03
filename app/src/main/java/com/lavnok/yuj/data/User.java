package com.lavnok.yuj.data;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String userId;
    public String email;
    boolean isInstructor;
    public String displayName;
    public int age;
    public String medicalHistory;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public User(String displayName,int age, String medicalHistory, boolean isInstructor) {
        this.displayName=displayName;
        this.age = age;
        this.medicalHistory = medicalHistory;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userDisplayName", displayName);
        result.put("age", age);
        result.put("MedHis", medicalHistory);
        return result;
    }

}
