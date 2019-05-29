package com.example.projekt_proz.activities.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;

public class TestEditQuestionsFragment extends Fragment {
    private String login;
    private String password;
    private prozTest currentTest;

    private RadioGroup radioGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_edit_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentTest = ((TestEditActivity) getActivity()).currentTest;
        login = ((TestEditActivity) getActivity()).login;
        password = ((TestEditActivity) getActivity()).password;

        radioGroup = view.findViewById(R.id.radio_group);

        new LoadQuestions().execute();
    }

    private void updateQuestions(List<prozQuestion> questionList) {
        radioGroup.removeAllViews();

        for(final prozQuestion question : questionList)
        {
            CheckBox button = new CheckBox(getActivity());
            button.setId(View.generateViewId());
            button.setText(question.getText());
            for(prozQuestion q : currentTest.getQuestions())
            {
                if (q.getQuestionID() == question.getQuestionID())
                    button.setChecked(true);
            }
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    for(prozQuestion q : currentTest.getQuestions())
                    {
                        if (q.getQuestionID() == question.getQuestionID())
                        {
                            if (b)
                                return;
                            else
                            {
                                currentTest.getQuestions().remove(q);
                                break;
                            }
                        }
                    }
                    if (b)
                        currentTest.getQuestions().add(question);
                }
            });

            button.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f));
            button.setBackgroundColor(getResources().getColor(R.color.colorTestQuestion));
            radioGroup.addView(button);
        }
    }

    public class LoadQuestions extends AsyncTask<Void, Void, List<prozQuestion>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "", "Ładowanie pytań", true);
        }

        @Override
        protected List<prozQuestion> doInBackground(Void... voids) {
            try {
                return new MyClient().questions().listQuestions(Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<prozQuestion> testList) {
            dialog.cancel();
            if (testList == null) {
                Toast.makeText(getActivity(), "Nie udało się pobrać pytań!", Toast.LENGTH_LONG).show();
                getActivity().finish();
                return;
            }
            updateQuestions(testList);
        }
    }
}
