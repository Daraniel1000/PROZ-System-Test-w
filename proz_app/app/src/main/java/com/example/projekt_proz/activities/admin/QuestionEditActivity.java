package com.example.projekt_proz.activities.admin;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt_proz.R;

import com.example.projekt_proz.adapters.AnswerViewAdapter;
import com.example.projekt_proz.models.QuestionListWrapper;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;

import java.util.ArrayList;


public class QuestionEditActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<prozAnswer> answerList;

    private EditText editText;
    private QuestionListWrapper qlw;
    private int cq;
    private prozQuestion currentQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_question_edit);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AnswerViewAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        editText=findViewById(R.id.text_input_question_name);

        answerList = new ArrayList<>();
        qlw = (QuestionListWrapper) getIntent().getSerializableExtra("qlw");
        if (qlw == null)
        {
            prozQuestion q = (prozQuestion) getIntent().getSerializableExtra("cur_question");
            // TODO: fix this mess
        }
        cq=qlw.getCurQuestion();
        currentQuestion=qlw.getProzQuestionArrayList().get(cq);

        editText.setText(currentQuestion.getText());
        answerList= currentQuestion.getAnswers();

        ((AnswerViewAdapter) recyclerView.getAdapter()).setAnswerList(QuestionEditActivity.this.answerList,false,false);
    }
    public void updateAnswers(View view){
        for(int x=0;x<answerList.size();x++){
            CheckBox cb = findViewById(300+x).findViewById(R.id.cbCorrect);
            EditText et= findViewById(300+x).findViewById(R.id.teAnswer);
            Log.d("","x:"+x);
            answerList.get(x).setCorrect(cb.isChecked());
            answerList.get(x).setText(et.getText().toString());

        }
    }

    public void addAnswer(View view){
/*TODO: fix crashes for 6+ items for del+ins+del sequence */
        if(answerList.size()==5){Toast.makeText(QuestionEditActivity.this, "Nie da się dodać więcej odpowiedzi!", Toast.LENGTH_LONG).show();return;}
        updateAnswers(view);
        prozAnswer pa = new prozAnswer(false,"Nowa odpowiedź "+(answerList.size()+1));
        answerList.add(pa);
        ((AnswerViewAdapter) recyclerView.getAdapter()).setAnswerList(QuestionEditActivity.this.answerList,true,false);


        recyclerView.scrollToPosition(answerList.size()-1);

    }
    public void deleteAnswer(View view){
        if(answerList.isEmpty())return;
        updateAnswers(view);
        answerList.remove(answerList.size()-1);
        ((AnswerViewAdapter) recyclerView.getAdapter()).setAnswerList(QuestionEditActivity.this.answerList,false,true);



        recyclerView.scrollToPosition(answerList.size()-1);
    }
    public void onBackPressed() {
        saveQuestion(findViewById(R.id.button11));
    }
    public void saveQuestion(View view){
        updateAnswers(view);
        Intent i = new Intent(QuestionEditActivity.this, TestEditActivity.class);
        currentQuestion.setAnswers(answerList);
        currentQuestion.setText(editText.getText().toString());
        int correctAns=0;
        for(int x=0;x<answerList.size();x++){
            CheckBox cb = findViewById(300+x).findViewById(R.id.cbCorrect);
            if(cb.isChecked())correctAns++;
        }
        Log.d("QuestionEditActivity","correct answers "+correctAns);
        if(correctAns==0){
            Toast.makeText(QuestionEditActivity.this, "Nie zaznaczono poprawnych odpowiedzi!", Toast.LENGTH_LONG).show();return;}
       else if(correctAns==1){currentQuestion.setType(prozQuestion.TYPE_SINGLE_CHOICE);}
       else {currentQuestion.setType(prozQuestion.TYPE_MULTIPLE_CHOICE);}
       qlw.replaceQuestion(cq,currentQuestion);
       i.putExtra("qlw",qlw);
       i.putExtra("edited",true);
        startActivity(i);
    }
 

}
