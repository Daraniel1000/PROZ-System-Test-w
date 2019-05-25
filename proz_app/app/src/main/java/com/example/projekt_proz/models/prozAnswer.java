package com.example.projekt_proz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class prozAnswer implements Serializable {
    private int answerID;
    private boolean correct;
    private String text;

    public void setText(String text) {
        this.text = text;
    }



    public void setCorrect(boolean correct) {
        this.correct = correct;
    }


    public prozAnswer() {
    }

    public prozAnswer(int id, boolean c, String t)
    {
        answerID = id;
        correct = c;
        text = t;
    }

    public prozAnswer( boolean c, String t)
    {

        correct = c;
        text = t;
    }

    public int getAnswerID() {
        return answerID;
    }

    @JsonIgnore
    public boolean isCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }
}
