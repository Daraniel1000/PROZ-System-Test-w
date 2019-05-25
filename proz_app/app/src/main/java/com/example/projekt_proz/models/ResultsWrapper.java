package com.example.projekt_proz.models;

import java.util.ArrayList;

public class ResultsWrapper {
    public ArrayList<prozResults> getProzResultsArrayList() {
        return prozResultsArrayList;
    }

    public void setProzResultsArrayList(ArrayList<prozResults> prozResultsArrayList) {
        this.prozResultsArrayList = prozResultsArrayList;
    }

   private ArrayList<prozResults> prozResultsArrayList;
}
