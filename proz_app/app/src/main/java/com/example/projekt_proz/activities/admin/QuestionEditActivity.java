package com.example.projekt_proz.activities.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
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
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;


public class QuestionEditActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private EditText editText;
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

        if (savedInstanceState == null)
        {
            currentQuestion = (prozQuestion) getIntent().getSerializableExtra("cur_question");
            if (currentQuestion == null)
            {
                // Creating a new question
                currentQuestion = new prozQuestion();
                currentQuestion.initAnswers(0);
            }
        }
        else
        {
            currentQuestion = (prozQuestion) savedInstanceState.getSerializable("cur_question");
        }

        editText.setText(currentQuestion.getText());
        ((AnswerViewAdapter) recyclerView.getAdapter()).setAnswerList(currentQuestion.getAnswers());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        currentQuestion.setText(currentQuestion.getText());
        outState.putSerializable("cur_question", currentQuestion);
    }

    public void updateAnswers(View view){
        for(int x=0;x<currentQuestion.getAnswersSize();x++){
            CheckBox cb = findViewById(300+x).findViewById(R.id.cbCorrect);
            EditText et= findViewById(300+x).findViewById(R.id.teAnswer);
            Log.d("","x:"+x);
            currentQuestion.getAnswer(x).setCorrect(cb.isChecked());
            currentQuestion.getAnswer(x).setText(et.getText().toString());
        }
    }

    public void addAnswer(View view){
/*TODO: fix crashes for 6+ items for del+ins+del sequence */
        if(currentQuestion.getAnswersSize()==5){Toast.makeText(QuestionEditActivity.this, "Nie da się dodać więcej odpowiedzi!", Toast.LENGTH_LONG).show();return;}
        updateAnswers(view);
        prozAnswer pa = new prozAnswer(false,"Nowa odpowiedź "+(currentQuestion.getAnswersSize()+1));
        currentQuestion.getAnswers().add(pa);
        recyclerView.getAdapter().notifyDataSetChanged();


        recyclerView.scrollToPosition(currentQuestion.getAnswersSize()-1);

    }
    public void deleteAnswer(View view){
        if(currentQuestion.getAnswers().isEmpty())return;
        updateAnswers(view);
        currentQuestion.getAnswers().remove(currentQuestion.getAnswersSize()-1);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(currentQuestion.getAnswersSize()-1);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setTitle("Zapisywanie zmian")
            .setMessage("Czy chcesz zapisać zmiany?")
            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveQuestion();
                    supportFinishAfterTransition();
                }

            })
            .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    supportFinishAfterTransition();
                }
            })
            .setNeutralButton("Anuluj", null)
            .setCancelable(true)
            .show();
    }

    public void saveQuestion(){
        currentQuestion.setAnswers(currentQuestion.getAnswers());
        currentQuestion.setText(editText.getText().toString());
        int correctAns=0;
        for(int x=0;x<currentQuestion.getAnswersSize();x++){
            CheckBox cb = findViewById(300+x).findViewById(R.id.cbCorrect);
            if(cb.isChecked())correctAns++;
        }
        Log.d("QuestionEditActivity","correct answers "+correctAns);
        if(correctAns==0){
            Toast.makeText(QuestionEditActivity.this, "Nie zaznaczono poprawnych odpowiedzi!", Toast.LENGTH_LONG).show();return;}
       else if(correctAns==1){currentQuestion.setType(prozQuestion.TYPE_SINGLE_CHOICE);}
       else {currentQuestion.setType(prozQuestion.TYPE_MULTIPLE_CHOICE);}
       // TODO: multiple_choice z jedną poprawną też byłby sensowny :P

        // TODO: wyślij do serwera
    }
 

}
