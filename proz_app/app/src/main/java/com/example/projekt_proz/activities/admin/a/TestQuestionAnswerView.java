package com.example.projekt_proz.activities.admin.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projekt_proz.R;

import com.example.projekt_proz.adapters.AdminAnswerViewAdapter;
import com.example.projekt_proz.adapters.AdminQuestionsViewAdapter;
import com.example.projekt_proz.adapters.AdminTestViewAdapter;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;

public class TestQuestionAnswerView extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private ArrayList<prozAnswer> prozAnswers;
    private prozQuestion currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentQuestion=(prozQuestion) getIntent().getSerializableExtra("cur_question");
        prozAnswers=currentQuestion.getAnswers();
        setContentView(R.layout.a_activity_test_question_answer_view);

        TextView tv = findViewById(R.id.text11);
        tv.setText(currentQuestion.getText());

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminAnswerViewAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ((AdminAnswerViewAdapter) recyclerView.getAdapter()).setAnswerList(prozAnswers);

        return;
    }


}
