package com.example.projekt_proz.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.ResultsResponse;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
    private ResultsResponse results;

    private Handler timerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_within_test);
        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");
        test = (prozTest) getIntent().getSerializableExtra("test");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new TestQuestionsPagerAdapter(getSupportFragmentManager(), test.getQuestions(), results));

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        if (savedInstanceState != null)
        {
            questionsAnswers = (HashMap<Integer, ArrayList<Integer>>) savedInstanceState.getSerializable("questionsAnswers");
            results = (ResultsResponse) savedInstanceState.getSerializable("results");
            if (results != null)
            {
                updateResultsUI();
            }
        }
        else
        {
            new DownloadAnswers().execute();
        }

        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();

                long endTime = test.getEndDate().getTime();
                long currentTime = Calendar.getInstance().getTime().getTime();
                if (currentTime > endTime && results == null)
                {
                    // Out of time - send results now
                    sendTestResults();
                }
                else
                {
                    timerHandler.postDelayed(this, 1000);
                }
            }
        };

        timerHandler.postDelayed(timerRunnable, 1000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("questionsAnswers", questionsAnswers);
        outState.putSerializable("results", results);
    }

    @Override
    public void onBackPressed() {
        askAbortTest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (results == null)
        {
            getMenuInflater().inflate(R.menu.timer, menu);

            long endTime = test.getEndDate().getTime();
            long currentTime = Calendar.getInstance().getTime().getTime();
            long remainingTime = (endTime - currentTime) / 1000;

            menu.findItem(R.id.countdown).setTitle(String.format("%02d:%02d", remainingTime / 60, remainingTime % 60));
        }
        else
        {
            getMenuInflater().inflate(R.menu.score, menu);
            menu.findItem(R.id.score).setTitle("Wynik: " + results.getResults().getPoints() + " pkt");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            askAbortTest();
            return true;
        }

        if (id == R.id.end_test) {
            finishTest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void askAbortTest() {
        if (results == null) {
            new AlertDialog.Builder(this)
                .setTitle("Wychodzenie z testu")
                .setMessage("Czy na pewno chcesz wyjść z testu? Twoje odpowiedzi nie zostaną zapisane!")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        supportFinishAfterTransition();
                    }

                })
                .setNegativeButton("Nie", null)
                .show();
        } else {
            supportFinishAfterTransition();
        }
    }

    @Override
    public void nextQuestion() {
        int tab = viewPager.getCurrentItem();
        tab++;
        viewPager.setCurrentItem(tab);
    }

    @Override
    public void finishTest() {
        new AlertDialog.Builder(this)
            .setTitle("Kończenie testu")
            .setMessage("Czy na pewno chcesz zakończyć test?")
            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendTestResults();
                }

            })
            .setNegativeButton("Nie", null)
            .show();
    }

    private void sendTestResults() {
        List<Integer> solutions = new ArrayList<>();
        for(List<Integer> questionSolutions : questionsAnswers.values())
            solutions.addAll(questionSolutions);
        new SubmitTest(solutions).execute();
    }

    @Override
    public void answersChanged(int question, ArrayList<Integer> answers) {
        questionsAnswers.put(question, answers);
    }

    @Override
    public void returnToMenu() {
        supportFinishAfterTransition();
    }

    private class TestQuestionsPagerAdapter extends FragmentStatePagerAdapter {
        private final List<prozQuestion> questionList;
        private ResultsResponse results;

        TestQuestionsPagerAdapter(FragmentManager fm, List<prozQuestion> questionList, ResultsResponse results) {
            super(fm);
            this.questionList = questionList;
            this.results = results;
        }

        @Override
        public Fragment getItem(int i) {
            return TestQuestionFragment.newInstance(
                i + 1, questionList.get(i), i == getCount() - 1,
                results != null ? results.getResults().getAnswerID() : null,
                results != null ? results.getCorrectAnswers() : null);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            // Force the fragments to update
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return questionList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        public void setResults(ResultsResponse results) {
            this.results = results;
            notifyDataSetChanged();
        }
    }

    private class DownloadAnswers extends AsyncTask<Void, Void, ResultsResponse> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(WithinTest.this, "", "Wczytywanie", true);
        }

        @Override
        protected ResultsResponse doInBackground(Void... voids) {
            try {
                return new MyClient().tests().getResults(test.getTestID(), Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResultsResponse results) {
            dialog.cancel();
            if (results != null) {
                WithinTest.this.results = results;
                WithinTest.this.updateResultsUI();
            }
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
                WithinTest.this.results = results;
                WithinTest.this.updateResultsUI();
            } else {
                Toast.makeText(WithinTest.this, "Nie udało się wysłać odpowiedzi!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateResultsUI() {
        ((TestQuestionsPagerAdapter) viewPager.getAdapter()).setResults(results);
        invalidateOptionsMenu();
    }
}