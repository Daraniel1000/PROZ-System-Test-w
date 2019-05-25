package com.example.projekt_proz.activities.admin.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projekt_proz.R;

import com.example.projekt_proz.adapters.AdminResultsViewAdapter;
import com.example.projekt_proz.adapters.AdminQuestionsViewAdapter;
import com.example.projekt_proz.adapters.AdminTestViewAdapter;
import com.example.projekt_proz.models.ResultsWrapper;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultsView extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private ArrayList<prozResults> pResults;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_results_view);
        ResultsWrapper rw =(ResultsWrapper)getIntent().getSerializableExtra("resuts");
        pResults=rw.getProzResultsArrayList();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminResultsViewAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ((AdminResultsViewAdapter) recyclerView.getAdapter()).setResultsList(pResults);

        return;
    }


    class SortbyPoints implements Comparator<prozResults>
    {

        public int compare(prozResults  a, prozResults b)
        {
            return a.getPoints()-b.getPoints();
        }
    }

    class SortbyResultsID implements Comparator<prozResults>
    {

        public int compare(prozResults  a, prozResults b)
        {
            return a.getResultsID()-b.getResultsID();
        }
    }
    class SortbyTestID implements Comparator<prozResults>
    {

        public int compare(prozResults  a, prozResults b)
        {
            return a.getTestID()-b.getTestID();
        }
    }
    class SortbyUserID implements Comparator<prozResults>
    {

        public int compare(prozResults  a, prozResults b)
        {
            return a.getUserID()-b.getUserID();
        }
    }
    class SortbyTIME implements Comparator<prozResults>
    {

        public int compare(prozResults  a, prozResults b)
        {
            return a.getSentDate().compareTo(b.getSentDate());
        }
    }
    public void sortByPoints(View view){
        Collections.sort(pResults,new SortbyPoints());
        ((AdminResultsViewAdapter) recyclerView.getAdapter()).setResultsList(pResults);
    }
    public void sortByResultID(View view){
        Collections.sort(pResults,new SortbyResultsID());
        ((AdminResultsViewAdapter) recyclerView.getAdapter()).setResultsList(pResults);
    }
    public void sortByUserID(View view){
        Collections.sort(pResults,new SortbyUserID());
        ((AdminResultsViewAdapter) recyclerView.getAdapter()).setResultsList(pResults);
    }
    public void sortByTestID(View view){
        Collections.sort(pResults,new SortbyTestID());
        ((AdminResultsViewAdapter) recyclerView.getAdapter()).setResultsList(pResults);
    }
    public void sortByTime(View view){
        Collections.sort(pResults,new SortbyTIME());
        ((AdminResultsViewAdapter) recyclerView.getAdapter()).setResultsList(pResults);
    }


}
