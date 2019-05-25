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
import android.widget.Toast;

import com.example.projekt_proz.R;

import com.example.projekt_proz.adapters.QuestionViewAdapter;
import com.example.projekt_proz.models.QuestionListWrapper;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;

public class TestCreation  extends AppCompatActivity implements QuestionViewAdapter.OnQuestionClickListener{
    private RecyclerView recyclerView;
    private ArrayList<prozQuestion> questionList;

    private EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_test_creation);
        text=findViewById(R.id.text_input_test_name);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new QuestionViewAdapter(this, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        boolean edited=getIntent().getBooleanExtra("edited",false);
        if(edited==false){

        questionList = new ArrayList<prozQuestion>();
        populate(questionList);
        }
        else{
            QuestionListWrapper qlw =(QuestionListWrapper)getIntent().getSerializableExtra("qlw");
            questionList=qlw.getProzQuestionArrayList();

            String s = qlw.getTestName();
            text.setText(s);

        }
        ((QuestionViewAdapter) recyclerView.getAdapter()).setQuestionList(TestCreation.this.questionList);
    }

    void populate(ArrayList<prozQuestion> questionList){
        prozAnswer yay = new prozAnswer(true,"Prawidłowa odpowiedź");
        prozAnswer nay = new prozAnswer(false,"Niepoprawna odpowiedź");

        prozQuestion dummy1= new prozQuestion("Przykładowe pytanie "+(questionList.size()+1),10);
        dummy1.addAnswer(yay);
        dummy1.addAnswer(nay);
        questionList.add(dummy1);

    }
    public void addQuestion(View view){
        prozAnswer yay = new prozAnswer(1,true,"Prawidłowa odpowiedź");
        prozAnswer nay = new prozAnswer(2,false,"Niepoprawna odpowiedź");

        prozQuestion dummy1= new prozQuestion("Nowe pytanie "+(questionList.size()+1),10);
        dummy1.addAnswer(yay);
        dummy1.addAnswer(nay);
        questionList.add(dummy1);
        ((QuestionViewAdapter) recyclerView.getAdapter()).setQuestionList(TestCreation.this.questionList);
        recyclerView.scrollToPosition(questionList.size()-1);
    }
    public void deleteQuestion(View view){
        if(questionList.isEmpty())return;
        questionList.remove(questionList.size()-1);
        ((QuestionViewAdapter) recyclerView.getAdapter()).setQuestionList(TestCreation.this.questionList);
        recyclerView.scrollToPosition(questionList.size()-1);
    }
    public void onBackPressed() {
        Intent i = new Intent(TestCreation.this, AdminHomeScreen.class);
        startActivity(i);
    }
    public void saveTest(View view){
        String title= text.getText().toString();
        if(title.length()==0){
            Toast.makeText(TestCreation.this, "Nie podano nazwy testu!", Toast.LENGTH_LONG).show();return;}
        prozTest pTest = new prozTest();
        pTest.setQuestions(questionList);
        pTest.setTitle(title);
        /*TODO:ask about start and finish date, send test*/

    }
    @Override
    public void onQuestionClick(CardView view, int position){

        Editable e= text.getText();
        String s = e.toString();

        Intent i = new Intent(TestCreation.this, TestCreationQuestion.class);
        QuestionListWrapper questionListWrapper = new QuestionListWrapper(questionList,position,s);

        i.putExtra("qlw",questionListWrapper);
        startActivity(i);
    }
}
