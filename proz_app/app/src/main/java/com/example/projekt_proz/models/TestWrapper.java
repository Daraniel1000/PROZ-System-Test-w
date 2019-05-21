package com.example.projekt_proz.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TestWrapper implements Serializable {
    private prozTest test;
    private prozResults result;
    private int currentQuestionNumber;
    private int maxQuestionNumber;

    public TestWrapper( prozTest test, prozResults result) {
        this.test=test;
        this.result=result;
        currentQuestionNumber=0;
        maxQuestionNumber=test.getQuestionsSize()-1;
    }


    public prozTest getTest() {
        return test;
    }

    public void setTest(prozTest test) {
        this.test = test;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }
    public void incQuestion(){++currentQuestionNumber;}

    public void setCurrentQuestionNumber(int currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }

    public prozResults getResult() {
        return result;
    }

    public void setResult(prozResults result) {
        this.result = result;
    }

    public int getMaxQuestionNumber() {
        return maxQuestionNumber;
    }


}
