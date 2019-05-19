package com.example.projekt_proz.models;

import java.io.Serializable;

public class prozAnswer implements Serializable {
    private int answerID;
    private boolean correct;
    private String text;

    public prozAnswer(int id, boolean c, String t)
    {
        answerID = id;
        correct = c;
        text = t;
    }


    public int getAnswerID() {
        return answerID;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }
}