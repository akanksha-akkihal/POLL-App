package com.example.pollingapp;

import java.security.Timestamp;

public class pollInfo {
    private String question;
    private String option1;
    private String option2;
    private String userId;
    private String docId;
    private int count1;
    private int count2;

    public pollInfo() {
    }

    public pollInfo(String question, String option1, String option2, String userId, String docId, int count1, int count2) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.userId = userId;
        this.docId = docId;
        this.count1 = count1;
        this.count2 = count2;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }
}
