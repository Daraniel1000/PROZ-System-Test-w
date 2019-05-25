package com.example.projekt_proz.models;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionListWrapper implements Serializable {
    private ArrayList<prozQuestion> prozQuestionArrayList;
    private int curQuestion;
    private String testName;

    public void replaceQuestion(int number,prozQuestion question){
        prozQuestionArrayList.set(number,question);
    }
    public String getTestName() {
        return testName;
    }


    public QuestionListWrapper(ArrayList<prozQuestion> prozQuestionArrayList, int curQuestion, String testName) {
        this.prozQuestionArrayList = prozQuestionArrayList;
        this.curQuestion = curQuestion;
        this.testName = testName;
    }

    public int getCurQuestion() {
        return curQuestion;
    }



    public ArrayList<prozQuestion> getProzQuestionArrayList() {
        return prozQuestionArrayList;
    }


   
}
