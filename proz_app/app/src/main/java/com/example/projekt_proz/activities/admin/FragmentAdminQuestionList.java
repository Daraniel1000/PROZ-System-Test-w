package com.example.projekt_proz.activities.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekt_proz.R;
import com.example.projekt_proz.adapters.AdminQuestionsViewAdapter;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.tasks.FetchQuestion;
import com.example.projekt_proz.tasks.FetchQuestions;
import com.example.projekt_proz.tasks.FetchTest;
import com.example.projekt_proz.tasks.FetchTests;

import java.util.ArrayList;


public class FragmentAdminQuestionList extends Fragment implements AdminQuestionsViewAdapter.OnQuestionClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private ArrayList<prozQuestion> questionsList = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.a_fragment_question_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminQuestionsViewAdapter(getActivity(), questionsList, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        refreshLayout = view.findViewById(R.id.swipe_container);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark);

        new FetchQuestions(getActivity(), getArguments().getString("login"), getArguments().getString("password"), questionsList, recyclerView, refreshLayout).execute();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), QuestionEditActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onQuestionClick(CardView view, int position) {
        new FetchQuestion(getActivity(), QuestionEditActivity.class, getArguments().getString("login"), getArguments().getString("password"), view, questionsList.get(position).getQuestionID()).execute();
    }

    @Override
    public void onRefresh() {
        new FetchQuestions(getActivity(), getArguments().getString("login"), getArguments().getString("password"), questionsList, recyclerView, refreshLayout).execute();
    }
}
