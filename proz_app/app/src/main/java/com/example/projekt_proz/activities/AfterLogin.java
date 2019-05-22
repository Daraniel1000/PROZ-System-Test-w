package com.example.projekt_proz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.TestWrapper;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.adapters.TestViewAdapter;

import java.util.ArrayList;

public class AfterLogin extends AppCompatActivity implements TestViewAdapter.OnTestClickListener {
    private static final String TAG = "AfterLogin";


    private ArrayList<prozTest> prozTestList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        recyclerView = findViewById(R.id.recycler);
        prozTestList = populateList();
        TestViewAdapter testViewAdapter = new TestViewAdapter(this, prozTestList, this);
        recyclerView.setAdapter(testViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    private ArrayList<prozTest> populateList() {
        /*TODO:GET TESTS, REMOVE THOSE EXPIRED*/
        ArrayList<prozTest> list = new ArrayList<>();
        ArrayList<prozQuestion> qlist1 = new ArrayList<>();
        ArrayList<prozAnswer> alist = new ArrayList<>();

        alist.add(new prozAnswer(1, false, "tak"));
        alist.add(new prozAnswer(2, true, "nie"));
        alist.add(new prozAnswer(3, false, "nie wiem11111111111111111111111111111111111111"));


        prozQuestion q1 = new prozQuestion(1, 1, "costamcostam1", 3);
        q1.setAnswers(alist);

        prozQuestion q2 = new prozQuestion(2, 1, "costamcostam2", 3);
        q2.setAnswers(alist);

        prozQuestion q3 = new prozQuestion(3, 1, "costamcostam3", 3);
        q3.setAnswers(alist);

        qlist1.add(q1);
        qlist1.add(q2);
        qlist1.add(q3);

        prozTest dummy1 = new prozTest(1, "dummy1 3 questions", "20.03.2007", "30.05.2020", 1, qlist1.size());
        dummy1.setQuestions(qlist1);
        prozTest dummy2 = new prozTest(2, "dummy2 3 questions", "20.03.2007", "30.05.2020", 1, qlist1.size());
        dummy2.setQuestions(qlist1);
        prozTest dummy3 = new prozTest(2, "dummy3 3 questions", "20.03.2007", "30.05.2020", 1, qlist1.size());
        dummy3.setQuestions(qlist1);
        prozTest dummy4 = new prozTest(2, "dummy4 3 questions", "20.03.2007", "30.05.2020", 1, qlist1.size());
        dummy4.setQuestions(qlist1);
        prozTest dummy5 = new prozTest(2, "dummy5 3 questions", "20.03.2007", "30.05.2020", 1, qlist1.size());
        dummy5.setQuestions(qlist1);
        list.add(dummy1);
        list.add(dummy2);
        list.add(dummy3);
        list.add(dummy4);
        list.add(dummy5);
        return list;
    }

    public void backToMain(View view) {
        startActivity(new Intent(AfterLogin.this, MainActivity.class));
    }

    @Override
    public void onTestClick(int position) {
        Log.d(TAG, "test clicked:" + position);
        /*TODO:INITIATE RESULTS WITH APPROPRIATE VALUES*/
        prozResults result = new prozResults(0, 1, 0);
        result.initAnswers(prozTestList.get(position).getQuestionsSize());
        TestWrapper tw = new TestWrapper(prozTestList.get(position), result);

        Intent intent = new Intent(AfterLogin.this, WithinTest.class);

        intent.putExtra("TEST", tw);
        startActivity(intent);
    }
}

