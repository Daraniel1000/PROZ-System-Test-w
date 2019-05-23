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
import java.util.List;

public class TestQuestionFragment extends Fragment {
    private static final String ARG_QUESTION = "question";
    private static final String ARG_QUESTION_NUMBER = "questionNumber";
    private static final String ARG_IS_LAST = "isLast";
    private static final String ARG_SELECTED_ANSWERS = "selectedAnswers";
    private static final String ARG_CORRECT_ANSWERS = "correctAnswers";

    private prozQuestion question;
    private int questionNumber;
    private boolean isLast;
    private ArrayList<Integer> selectedAnswers;
    private ArrayList<Integer> correctAnswers;

    private boolean[] answers;

    private OnFragmentInteractionListener mListener;

    public static TestQuestionFragment newInstance(int questionNumber, prozQuestion question, boolean isLast, ArrayList<Integer> selectedAnswers, ArrayList<Integer> correctAnswers) {
        TestQuestionFragment fragment = new TestQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        args.putInt(ARG_QUESTION_NUMBER, questionNumber);
        args.putBoolean(ARG_IS_LAST, isLast);
        args.putSerializable(ARG_SELECTED_ANSWERS, selectedAnswers);
        args.putSerializable(ARG_CORRECT_ANSWERS, correctAnswers);
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
            selectedAnswers = (ArrayList<Integer>) getArguments().getSerializable(ARG_SELECTED_ANSWERS);
            correctAnswers = (ArrayList<Integer>) getArguments().getSerializable(ARG_CORRECT_ANSWERS);
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
            if (selectedAnswers == null)
                button.setChecked(answers[i]);
            else
                button.setChecked(selectedAnswers.contains(question.getAnswer(i).getAnswerID()));
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
            button.setBackgroundColor(getResources().getColor(R.color.colorTestQuestion));
            if (correctAnswers != null)
            {
                button.setEnabled(false);
                if (correctAnswers.contains(question.getAnswer(i).getAnswerID()))
                {
                    if (selectedAnswers != null && selectedAnswers.contains(question.getAnswer(i).getAnswerID()))
                        button.setTextColor(getResources().getColor(R.color.colorAnswerCorrect)); // zaznaczył dobrze
                    else
                        button.setTextColor(getResources().getColor(R.color.colorAnswerIncorrect)); // nie zaznaczył a powinien
                }
                else if (selectedAnswers != null && selectedAnswers.contains(question.getAnswer(i).getAnswerID()) && question.getType() != prozQuestion.TYPE_SINGLE_CHOICE)
                    button.setTextColor(getResources().getColor(R.color.colorAnswerIncorrect)); // zaznaczył ale nie powinien
            }
            radioGroup.addView(button);
        }

        if (isLast) {
            if (correctAnswers != null)
                btnNext.setText("Powrót do menu");
            else
                btnNext.setText("Zakończ Test");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast)
                {
                    if (correctAnswers != null)
                        mListener.returnToMenu();
                    else
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
        void returnToMenu();
    }
}
