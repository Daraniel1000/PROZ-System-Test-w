package com.example.projekt_proz.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class prozResults implements Serializable {
    private int resultsID,testID,userID,points;
    private Timestamp sentDate;
    private ArrayList<Integer> answerID;

    public prozResults(int resID, int tID, int uID,  int poi)
    {
        resultsID = resID;
        testID = tID;
        userID = uID;
        points = poi;
    }

    public prozResults(int resID, int tID, int uID)
    {
        resultsID = resID;
        testID = tID;
        userID = uID;
        points = 0;
    }

    public void initAnswers(int i)
    {

        answerID = new ArrayList<>(i);
    }

    public boolean addAnswerID(Integer ID)
    {
        return answerID.add(ID);
    }

    public Integer getAnswerID(int i)
    {
        return answerID.get(i);
    }

    public int getResultsID() {
        return resultsID;
    }

    public int getTestID() {
        return testID;
    }

    public int getUserID() {
        return userID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int i){points=i;}

    public void setSentDate(Timestamp ts){sentDate=ts;}

    public void addAnswer(int i){answerID.add(i);}

    public Timestamp getSentDate() {
        return sentDate;
    }
}