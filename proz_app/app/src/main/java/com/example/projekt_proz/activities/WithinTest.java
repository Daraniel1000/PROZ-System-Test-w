package com.example.projekt_proz.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.ResultsResponse;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Credentials;

public class WithinTest extends AppCompatActivity implements TestQuestionFragment.OnFragmentInteractionListener {
    private static final String TAG = "WithinTest";

    private ViewPager viewPager;

    private String login;
    private String password;
    private prozTest test;

    private HashMap<Integer, ArrayList<Integer>> questionsAnswers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_within_test);
        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");
        test = (prozTest) getIntent().getSerializableExtra("test");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new TestQuestionsPagerAdapter(getSupportFragmentManager(), test.getQuestions()));

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        if (savedInstanceState != null)
        {
            questionsAnswers = (HashMap<Integer, ArrayList<Integer>>) savedInstanceState.getSerializable("questionsAnswers");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("questionsAnswers", questionsAnswers);
    }

    @Override
    public void onBackPressed() {
        askAbortTest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            askAbortTest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void askAbortTest() {
        new AlertDialog.Builder(this)
            .setTitle("Wychodzenie z testu")
            .setMessage("Czy na pewno chcesz wyjść z testu? Twoje odpowiedzi nie zostaną zapisane!")
            .setPositiveButton("Tak", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    supportFinishAfterTransition();
                }

            })
            .setNegativeButton("Nie", null)
            .show();
    }

    @Override
    public void nextQuestion() {
        int tab = viewPager.getCurrentItem();
        tab++;
        viewPager.setCurrentItem(tab);
    }

    @Override
    public void finishTest() {
        List<Integer> solutions = new ArrayList<>();
        for(List<Integer> questionSolutions : questionsAnswers.values())
            solutions.addAll(questionSolutions);
        new SubmitTest(solutions).execute();
    }

    @Override
    public void answersChanged(int question, ArrayList<Integer> answers) {
        questionsAnswers.put(question, answers);
    }

    private class TestQuestionsPagerAdapter extends FragmentStatePagerAdapter {
        private final List<prozQuestion> questionList;

        TestQuestionsPagerAdapter(FragmentManager fm, List<prozQuestion> questionList) {
            super(fm);
            this.questionList = questionList;
        }

        @Override
        public Fragment getItem(int i) {
            return TestQuestionFragment.newInstance(i + 1, questionList.get(i), i == getCount() - 1);
        }

        @Override
        public int getCount() {
            return questionList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    private class SubmitTest extends AsyncTask<Void, Void, ResultsResponse> {
        private final List<Integer> solutions;
        private ProgressDialog dialog;

        private SubmitTest(List<Integer> solutions) {
            this.solutions = solutions;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(WithinTest.this, "", "Wysyłanie odpowiedzi", true);
        }

        @Override
        protected ResultsResponse doInBackground(Void... voids) {
            try {
                return new MyClient().tests().submitTest(test.getTestID(), solutions, Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResultsResponse results) {
            dialog.cancel();
            if (results != null) {
                Toast.makeText(WithinTest.this, "Gratulacje! Twój wynik to " + results.getResults().getPoints() + " punktów", Toast.LENGTH_SHORT).show();
                supportFinishAfterTransition();
            } else {
                Toast.makeText(WithinTest.this, "Nie udało się wysłać odpowiedzi!", Toast.LENGTH_LONG).show();
            }
        }
    }
}