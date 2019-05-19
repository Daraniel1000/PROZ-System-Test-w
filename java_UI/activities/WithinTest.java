package com.example.projekt_proz.activities;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imahugefailure.R;
import com.example.projekt_proz.models.TestWrapper;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;


public class WithinTest extends AppCompatActivity{
    private static final String TAG = "WITHIN TEST";

    private TestWrapper tw;
    private int QuestionNr;
    private int FinalQuestionNr;
    private RadioGroup radioGroup;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_within_test);
        tw  = (TestWrapper) getIntent().getSerializableExtra("TEST");
        prozTest CurrentTest = tw.getTest();
        QuestionNr=tw.getCurrentQuestionNumber();
        FinalQuestionNr = tw.getMaxQuestionNumber();
        prozQuestion CurrentQuestion=CurrentTest.getQuestion(QuestionNr);

        Log.d(TAG,"received test"+CurrentTest.getTitle());

        relativeLayout = findViewById(R.id.relative_layout);
        radioGroup = findViewById(R.id.radio_group);

        TextView tv = new TextView(this);
        tv.setText("Pytanie "+(QuestionNr+1)+": "+CurrentQuestion.getText());
        tv.setBackgroundColor(Color.parseColor("#E8D233"));
        tv.setTextSize(25);
        tv.setTextColor(Color.parseColor("#000000"));
        RelativeLayout.LayoutParams prm = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        prm.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        prm.setMargins(10,20,10,10);
        tv.setLayoutParams(prm);
        relativeLayout.addView(tv);



        for (int i = 0; i < CurrentQuestion.getAnswersSize(); i++) {


            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(10+i);

            radioButton.setText(CurrentQuestion.getAnswer(i).getText());

            radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f));
            radioButton.setTextSize(20);
            radioButton.setBackgroundColor(Color.parseColor("#E8D233"));
            radioGroup.addView(radioButton);

            TextView textView = new TextView(this);
            textView.setText("\n");
            radioGroup.addView(textView);


        }
        Button btn = relativeLayout.findViewById(R.id.button11);
        if(QuestionNr==FinalQuestionNr)btn.setText("Zakończ Test");
    }

    public void nextQuestion(View view){
        int i;
        for (i = 0; i < tw.getTest().getQuestion(QuestionNr).getAnswersSize(); i++){
            RadioButton rbtn = radioGroup.findViewById(10+i);
            if(rbtn.isChecked()==true)break;
        }
        if(i== tw.getTest().getQuestion(QuestionNr).getAnswersSize()){
            Toast.makeText(this,"Musisz wybrać odpowiedź! ",Toast.LENGTH_LONG).show(); return;
        }
        Log.d(TAG,"Checked button nr "+(i+1));
        prozResults tempResults = tw.getResult();
        tempResults.addAnswerID(tw.getTest().getQuestion(QuestionNr).getAnswer(i).getAnswerID());
        tw.setResult(tempResults);
        if(QuestionNr==FinalQuestionNr){
            /*TODO: ADD TIMESTAMP, SEND RESULT */
            Toast.makeText(this,"Odpowiedzi wysłano! ",Toast.LENGTH_LONG).show();
            startActivity(new Intent(WithinTest.this, AfterLogin.class));
        }
        else{
        tw.incQuestion();
        Intent intent = new Intent(WithinTest.this, WithinTest.class);

            intent.putExtra("TEST",tw);
            startActivity(intent);

    }
}

}