package com.example.projekt_proz.activities.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekt_proz.R;
import com.example.projekt_proz.adapters.AdminTestViewAdapter;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;

public class FragmentResultsList extends Fragment implements AdminTestViewAdapter.OnTestClickListener {
    private RecyclerView recyclerView;
    private ArrayList<prozTest> testList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.a_fragment_results_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        testList = populateTestingData();
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminTestViewAdapter(getActivity(), testList, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private ArrayList<prozTest> populateTestingData() {
        ArrayList<prozTest> prozTests = new ArrayList<>();
        ArrayList<prozQuestion> questionList = new ArrayList<>();
        prozAnswer yay = new prozAnswer(true, "Prawidłowa odpowiedź");
        prozAnswer nay = new prozAnswer(false, "Niepoprawna odpowiedź");

        prozQuestion dummy1 = new prozQuestion("Przykładowe pytanie " + (questionList.size() + 1), 10);
        dummy1.addAnswer(yay);
        dummy1.addAnswer(nay);
        prozQuestion dummy2 = new prozQuestion("Przykładowe pytanie " + (questionList.size() + 1), 10);
        questionList.add(dummy1);
        questionList.add(dummy2);
        prozTest pt = new prozTest();
        pt.setQuestions(questionList);
        pt.setTitle("sdasdasfasfa");
        prozTests.add(pt);
        return prozTests;
    }

    @Override
    public void onTestClick(CardView view, int position) {
        Intent i = new Intent(getActivity(), ResultsActivity.class);
        i.putExtra("test", testList.get(position));
        startActivity(i);
    }
}
