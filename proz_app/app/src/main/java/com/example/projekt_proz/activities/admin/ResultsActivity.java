package com.example.projekt_proz.activities.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.projekt_proz.R;

import com.example.projekt_proz.adapters.AdminResultsViewAdapter;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class ResultsActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;

    private prozTest currentTest;
    private ArrayList<prozResults> pResults;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_results_view);

        currentTest = (prozTest) getIntent().getSerializableExtra("test");
        if (savedInstanceState == null)
        {
            pResults = populateTestingData(currentTest);
        }
        else
        {
            pResults = (ArrayList<prozResults>) savedInstanceState.getSerializable("results");
        }

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminResultsViewAdapter(this, pResults));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    private static ArrayList<prozResults> populateTestingData(prozTest test) {
        ArrayList<prozResults> results = new ArrayList<>();
        results.add(new prozResults(test.getTestID(), 1, new Timestamp(Calendar.getInstance().getTime().getTime()), 5));
        results.add(new prozResults(test.getTestID(), 2, new Timestamp(Calendar.getInstance().getTime().getTime()), 7));
        return results;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("results", pResults);
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
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    public void sortByResultID(View view){
        Collections.sort(pResults,new SortbyResultsID());
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    public void sortByUserID(View view){
        Collections.sort(pResults,new SortbyUserID());
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    public void sortByTestID(View view){
        Collections.sort(pResults,new SortbyTestID());
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    public void sortByTime(View view){
        Collections.sort(pResults,new SortbyTIME());
        recyclerView.getAdapter().notifyDataSetChanged();
    }


}
