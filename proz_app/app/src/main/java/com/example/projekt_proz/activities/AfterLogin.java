package com.example.projekt_proz.activities;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.adapters.TestViewAdapter;
import com.example.projekt_proz.tasks.FetchTest;
import com.example.projekt_proz.tasks.FetchTests;

import java.util.ArrayList;
import java.util.Calendar;

public class AfterLogin extends AppCompatActivity implements TestViewAdapter.OnTestClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "AfterLogin";

    private String login;
    private String password;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private ArrayList<prozTest> testList = new ArrayList<>();

    private Handler timerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new TestViewAdapter(this, testList, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        refreshLayout = findViewById(R.id.swipe_container);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark);

        if (savedInstanceState != null)
        {
            testList = (ArrayList<prozTest>) savedInstanceState.getSerializable("tests");
            ((TestViewAdapter) recyclerView.getAdapter()).setTestList(testList);
        }
        else
        {
            new FetchTests(this, login, password, testList, recyclerView, refreshLayout).execute();
        }

        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                recyclerView.getAdapter().notifyDataSetChanged();
                timerHandler.postDelayed(this, 1000);
            }
        };

        timerHandler.postDelayed(timerRunnable, 1000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new FetchTests(this, login, password, testList, recyclerView, refreshLayout).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tests", testList);
    }

    @Override
    public void onRefresh() {
        new FetchTests(this, login, password, testList, recyclerView, refreshLayout).execute();
    }

    @Override
    public void onTestClick(CardView view, int position) {
        prozTest test = testList.get(position);

        long startTime = test.getStartDate().getTime();
        long endTime = test.getEndDate().getTime();
        long currentTime = Calendar.getInstance().getTime().getTime();

        if (startTime > currentTime)
        {
            Toast.makeText(this, "Ten test jeszcze się nie rozpoczął!", Toast.LENGTH_SHORT).show();
        }
        else if (currentTime > endTime && !test.isFinished())
        {
            Toast.makeText(this, "Ten test już się zakończył!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            new FetchTest(this, WithinTest.class, login, password, view, test.getTestID()).execute();
        }
    }

}

