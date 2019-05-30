package com.example.projekt_proz.activities.admin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekt_proz.R;
import com.example.projekt_proz.adapters.AdminResultsViewAdapter;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultsListFragment extends Fragment {
    private prozTest currentTest;
    private ArrayList<prozResults> pResults;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentTest = ((ResultsActivity) getActivity()).currentTest;
        pResults = ((ResultsActivity) getActivity()).pResults;

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminResultsViewAdapter(getActivity(), pResults));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
