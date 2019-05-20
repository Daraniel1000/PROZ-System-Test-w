package database_classes;

import java.sql.Timestamp;
import java.util.ArrayList;

public class prozResults {
    private int resultsID,testID,userID,points;
    private Timestamp sentDate;
    private ArrayList<Integer> answerID;

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

    public Timestamp getSentDate() {
        return sentDate;
    }
}
