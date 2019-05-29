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
import com.example.projekt_proz.adapters.AdminTestQuestionsViewAdapter;
import com.example.projekt_proz.models.prozTest;

public class TestEditQuestionsFragment extends Fragment implements AdminTestQuestionsViewAdapter.OnQuestionClickListener {
    private prozTest currentTest;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_edit_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentTest = ((TestEditActivity) getActivity()).currentTest;

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminTestQuestionsViewAdapter(getActivity(), this, currentTest.getQuestions()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public void addQuestion(View view) {
        // TODO: Miały być pytania z bazy pytań...
        /*prozQuestion q = ???;
        currentTest.getQuestions().add(q);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(currentTest.getQuestionsSize() - 1);*/
    }

    public void deleteQuestion(View view) {
        if (currentTest.getQuestions().isEmpty())
            return;
        currentTest.getQuestions().remove(currentTest.getQuestionsSize() - 1);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(currentTest.getQuestionsSize() - 1);
    }

    @Override
    public void onQuestionClick(CardView view, int position) {
        Intent i = new Intent(getActivity(), QuestionEditActivity.class);
        i.putExtra("question", currentTest.getQuestions().get(position));
        startActivity(i);
    }
}
