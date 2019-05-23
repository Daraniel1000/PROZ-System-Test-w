package com.example.projekt_proz.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozQuestion;

import java.util.ArrayList;

public class TestQuestionFragment extends Fragment {
    private static final String ARG_QUESTION = "question";
    private static final String ARG_QUESTION_NUMBER = "questionNumber";
    private static final String ARG_IS_LAST = "isLast";

    private prozQuestion question;
    private int questionNumber;
    private boolean isLast;

    private boolean[] answers;

    private OnFragmentInteractionListener mListener;

    public static TestQuestionFragment newInstance(int questionNumber, prozQuestion question, boolean isLast) {
        TestQuestionFragment fragment = new TestQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        args.putInt(ARG_QUESTION_NUMBER, questionNumber);
        args.putBoolean(ARG_IS_LAST, isLast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (prozQuestion) getArguments().getSerializable(ARG_QUESTION);
            questionNumber = getArguments().getInt(ARG_QUESTION_NUMBER);
            isLast = getArguments().getBoolean(ARG_IS_LAST);
            answers = new boolean[question.getAnswersSize()];
        }
        if (savedInstanceState != null) {
            answers = savedInstanceState.getBooleanArray("answers");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("answers", answers);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvQuestion = view.findViewById(R.id.tvQuestion);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        Button btnNext = view.findViewById(R.id.btnNext);

        tvQuestion.setText("Pytanie " + questionNumber + ": " + question.getText());

        for (int i = 0; i < question.getAnswersSize(); i++) {
            CompoundButton button;
            switch(question.getType()) {
                case prozQuestion.TYPE_SINGLE_CHOICE:
                    button = new RadioButton(getActivity());
                    break;

                case prozQuestion.TYPE_MULTIPLE_CHOICE:
                    button = new CheckBox(getActivity());
                    break;

                default:
                    throw new UnsupportedOperationException("Invalid question type");
            }

            button.setId(View.generateViewId());
            button.setText(question.getAnswer(i).getText());
            button.setChecked(answers[i]);
            final int finalI = i;
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    answers[finalI] = b;

                    ArrayList<Integer> ans = new ArrayList<>();
                    for(int j = 0; j < question.getAnswersSize(); j++) {
                        if (answers[j]) {
                            ans.add(question.getAnswer(j).getAnswerID());
                        }
                    }
                    mListener.answersChanged(question.getQuestionID(), ans);
                }
            });

            button.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f));
            button.setTextSize(20);
            button.setBackgroundColor(Color.parseColor("#E8D233"));
            radioGroup.addView(button);
        }

        if (isLast) {
            btnNext.setText("Zakończ Test");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast)
                {
                    mListener.finishTest();
                }
                else
                {
                    mListener.nextQuestion();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void nextQuestion();
        void finishTest();
        void answersChanged(int question, ArrayList<Integer> answers);
    }
}
