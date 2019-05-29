package pl.edu.pw.elka.proz.bandaimbecyli.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class prozResults implements Serializable {
    private int resultsID = -1,testID,userID,points;
    private Timestamp sentDate;
    private ArrayList<Integer> answerID;

    public prozResults() {
    }

    public prozResults(int tID, int uID, Timestamp sent, int poi)
    {
        testID = tID;
        userID = uID;
        points = poi;
        sentDate = sent;
    }

    public prozResults(int resID, int tID, int uID, Timestamp sent, int poi)
    {
        resultsID = resID;
        testID = tID;
        userID = uID;
        points = poi;
        sentDate = sent;
    }

    public prozResults(int resID, int tID, int uID, Timestamp sent)
    {
        resultsID = resID;
        testID = tID;
        userID = uID;
        points = 0;
        sentDate = sent;
    }

    public void initAnswers(int i)
    {
        if(answerID == null)
            answerID = new ArrayList<>(i);
    }

    public boolean addAnswerID(Integer ID)
    {
        return answerID.add(ID);
    }

    public void setResultsID(int r)
    {
        resultsID = r;
    }

    public Integer getAnswerID(int i)
    {
        return answerID.get(i);
    }

    @JsonIgnore
    public Integer getAnswerIDSize()
    {
        return answerID.size();
    }

    public ArrayList<Integer> getAnswerID() {
        return answerID;
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

    public void setPoints(int points) {
        this.points = points;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }
}
