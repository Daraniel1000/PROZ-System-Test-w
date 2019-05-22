package com.example.projekt_proz.models;

import java.io.Serializable;
import java.util.ArrayList;

public class prozQuestion implements Serializable {
    private int questionID, type;
    private String text;
    private ArrayList<prozAnswer> answers;

    public prozQuestion() {
    }

    public prozQuestion(int id, int ty, String te)
    {
        questionID = id;
        type = ty;
        text = te;
    }

    public void setAnswers(ArrayList<prozAnswer> aList)
    {
        answers = new ArrayList<>(aList);
    }

    public void initAnswers(int n)
    {
        if(answers == null)
            answers = new ArrayList<>(n);
    }

    public boolean addAnswer(prozAnswer ans)
    {
        return answers.add(ans);
    }

    public prozAnswer getAnswer(int i)
    {
        return answers.get(i);
    }

    public ArrayList<prozAnswer> getAnswers() {
        return answers;
    }

    public int getAnswersSize(){return answers.size();}

    public int getQuestionID() {
        return questionID;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
