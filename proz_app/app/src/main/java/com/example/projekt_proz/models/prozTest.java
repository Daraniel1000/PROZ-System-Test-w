package com.example.projekt_proz.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class prozTest implements Serializable {
    private int testID, type;

    private String title;
    private Date startDate, endDate;
    private ArrayList<prozQuestion> questions;

    public prozTest() {
    }

    public prozTest(int id, String ti, Date sd, Date ed, int ty)
    {
        testID = id;
        title = ti;
        startDate = sd;
        endDate = ed;
        type = ty;
    }

    public void setQuestions(ArrayList<prozQuestion> qList)
    {
        if (qList == null)
            questions = null;
        else
            questions = new ArrayList<>(qList);
    }

    public void initQuestions(int i)
    {
        if(questions == null)
            questions = new ArrayList<>(i);
    }

    public boolean addQuestion(prozQuestion q)
    {
        return questions.add(q);
    }

    public prozQuestion getQuestion(int i)
    {
        return questions.get(i);
    }

    public int getQuestionsSize()
    {
        return questions.size();
    }

    public ArrayList<prozQuestion> getQuestions() {
        return questions;
    }

    public int getTestID() {
        return testID;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getType() {
        return type;
    }

}
