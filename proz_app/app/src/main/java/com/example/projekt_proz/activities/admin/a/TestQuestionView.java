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

import com.example.projekt_proz.adapters.AdminQuestionsViewAdapter;
import com.example.projekt_proz.adapters.AdminTestViewAdapter;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;

public class TestQuestionView extends AppCompatActivity implements AdminQuestionsViewAdapter.OnQuestionClickListener{
    private RecyclerView recyclerView;
    private ArrayList<prozQuestion> prozQuestions;
    private prozTest currentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentTest=(prozTest)getIntent().getSerializableExtra("cur_test");
        prozQuestions=currentTest.getQuestions();
        setContentView(R.layout.a_activity_test_question_view);

        TextView tv = findViewById(R.id.text11);
        tv.setText(currentTest.getTitle());

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminQuestionsViewAdapter(this, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ((AdminQuestionsViewAdapter) recyclerView.getAdapter()).setQuestionList(prozQuestions);

        return;
    }

    @Override
    public void onQuestionClick(CardView view, int position){

        Intent i = new Intent(TestQuestionView.this, TestQuestionAnswerView.class);
        i.putExtra("cur_question",prozQuestions.get(position));
        startActivity(i);
        return;
    }
}
