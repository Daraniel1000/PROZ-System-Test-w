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

import com.example.projekt_proz.R;

import com.example.projekt_proz.adapters.AdminTestViewAdapter;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;

public class TestView extends AppCompatActivity implements AdminTestViewAdapter.OnAdminTestClickListener{
    private RecyclerView recyclerView;
    private ArrayList<prozTest> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_test_view);
       testList=populate();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AdminTestViewAdapter(this, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ((AdminTestViewAdapter) recyclerView.getAdapter()).setAdminTestList(testList);

        return;
    }
    private ArrayList<prozTest> populate(){
        ArrayList<prozTest> prozTests = new ArrayList<>();
        ArrayList<prozQuestion> questionList= new ArrayList<>();
        prozAnswer yay = new prozAnswer(true,"Prawidłowa odpowiedź");
        prozAnswer nay = new prozAnswer(false,"Niepoprawna odpowiedź");

        prozQuestion dummy1= new prozQuestion("Przykładowe pytanie "+(questionList.size()+1),10);
        dummy1.addAnswer(yay);
        dummy1.addAnswer(nay);
        prozQuestion dummy2= new prozQuestion("Przykładowe pytanie "+(questionList.size()+1),10);
        questionList.add(dummy1);
        questionList.add(dummy2);
        prozTest pt= new prozTest();
        pt.setQuestions(questionList);
        pt.setTitle("sdasdasfasfa");
        prozTests.add(pt);
        return prozTests;
    }
    @Override
    public void onAdminTestClick(CardView view, int position){



        Intent i = new Intent(TestView.this, TestQuestionView.class);


        i.putExtra("cur_test",testList.get(position));
        startActivity(i);
        return;
    }
}
