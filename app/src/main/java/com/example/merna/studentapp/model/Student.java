package com.example.merna.studentapp.model;


import com.example.merna.studentapp.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;

/**
 * Created by Merna on 3/7/2016.
 */
public class Student {

    private String studentID;
    private String studentName;
    private String UserEmail;
    private String enterLecture;
    private String outerLecture;
    private HashMap<String, Object> timestampLastChanged;
    private HashMap<String, Object> timestampCreated;
    private HashMap<String, Object> timestampUserJoined;

    public Student() {
    }


    public Student(String id,String name, String email, HashMap<String, Object> timestampJoined) {

                this.studentID = id;
                this.studentName=name;
                this.UserEmail = email;
                this.timestampUserJoined = timestampJoined;
            }

    public Student(String studentID, String studentName, String enterLecture, String outerLecture, HashMap<String, Object> timestampCreated) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.enterLecture = enterLecture;
        this.outerLecture = outerLecture;
        this.timestampCreated = timestampCreated;

        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public String getEnterLecture() {
        return enterLecture;
    }

    public String getOuterLecture() {
        return outerLecture;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong() {

        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    @JsonIgnore
    public long getTimestampCreatedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    public HashMap<String, Object> getTimestampUserJoined() {
        return timestampUserJoined;
    }

    public String getUserEmail() {
        return UserEmail;
    }
}
