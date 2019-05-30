package com.example.projekt_proz.activities.admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.projekt_proz.R;

import com.example.projekt_proz.adapters.AdminResultsViewAdapter;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class ResultsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    prozTest currentTest;
    ArrayList<prozResults> pResults;

    String login;
    String password;

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_results_view);

        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentTest = (prozTest) getIntent().getSerializableExtra("test");
        if (savedInstanceState == null)
        {
            pResults = populateTestingData(currentTest);
        }
        else
        {
            pResults = (ArrayList<prozResults>) savedInstanceState.getSerializable("results");
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
    }

    private static ArrayList<prozResults> populateTestingData(prozTest test) {
        ArrayList<prozResults> results = new ArrayList<>();
        results.add(new prozResults(test.getTestID(), 1, new Timestamp(Calendar.getInstance().getTime().getTime()), 5));
        results.add(new prozResults(test.getTestID(), 2, new Timestamp(Calendar.getInstance().getTime().getTime()), 7));
        return results;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("results", pResults);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.action_results_stats:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_results_questions:
                viewPager.setCurrentItem(1);
                break;
            case R.id.action_results_scores:
                viewPager.setCurrentItem(2);
                break;
        }

        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().findItem(R.id.action_results_stats).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.action_results_questions).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.action_results_scores).setChecked(false);

        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new ResultsStatsFragment();
                case 1:
                    return new ResultsQuestionStatsFragment();
                case 2:
                    return new ResultsListFragment();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
