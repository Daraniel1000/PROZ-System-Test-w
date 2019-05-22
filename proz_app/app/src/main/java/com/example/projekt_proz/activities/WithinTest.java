package com.example.projekt_proz.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozTest;

import java.util.List;

public class WithinTest extends AppCompatActivity implements TestQuestionFragment.OnFragmentInteractionListener {
    private static final String TAG = "WithinTest";

    private ViewPager viewPager;

    private prozTest test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_within_test);
        test = (prozTest) getIntent().getSerializableExtra("test");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new TestQuestionsPagerAdapter(getSupportFragmentManager(), test.getQuestions()));

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
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
        //TODO
        Toast.makeText(this, "Jeszcze nie zaimplementowane", Toast.LENGTH_LONG).show();
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
}