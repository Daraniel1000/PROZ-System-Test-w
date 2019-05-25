package com.example.projekt_proz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Credentials;

public class AfterLogin extends AppCompatActivity implements TestViewAdapter.OnTestClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "AfterLogin";

    private String login;
    private String password;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private ArrayList<prozTest> testList;

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
        recyclerView.setAdapter(new TestViewAdapter(this, this));
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
            ((TestViewAdapter) recyclerView.getAdapter()).setTestList(AfterLogin.this.testList);
        }
        else
        {
            new FetchTests().execute();
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
        new FetchTests().execute();
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
        new FetchTests().execute();
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
            new FetchTest(view, test.getTestID()).execute();
        }
    }

    private class FetchTests extends AsyncTask<Void, Void, List<prozTest>> {
        @Override
        protected void onPreExecute() {
            refreshLayout.setRefreshing(true);
        }

        @Override
        protected List<prozTest> doInBackground(Void... voids) {
            try {
                return new MyClient().tests().getAvailableTests(Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<prozTest> testList) {
            refreshLayout.setRefreshing(false);
            if (testList == null) {
                Toast.makeText(AfterLogin.this, "Nie udało się pobrać testów!", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            AfterLogin.this.testList = new ArrayList<>(testList);
            TestViewAdapter adapter = (TestViewAdapter) recyclerView.getAdapter();
            adapter.setTestList(AfterLogin.this.testList);
        }
    }

    private class FetchTest extends AsyncTask<Void, Void, prozTest> {
        private ProgressDialog dialog;
        private CardView card;

        private final int testId;

        private FetchTest(CardView card, int testId) {
            super();
            this.card = card;
            this.testId = testId;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(AfterLogin.this, "", "Ładowanie testu", true);
        }

        @Override
        protected prozTest doInBackground(Void... voids) {
            try {
                return new MyClient().tests().getTest(testId, Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(prozTest test) {
            dialog.cancel();
            if (test == null) {
                Toast.makeText(AfterLogin.this, "Nie udało się załadować testu!", Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(AfterLogin.this, WithinTest.class);
            intent.putExtra("login", login);
            intent.putExtra("password", password);
            intent.putExtra("test", test);
            ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(AfterLogin.this,
                    card,
                    "testActivity"
                );
            ActivityCompat.startActivity(AfterLogin.this, intent, options.toBundle());
        }
    }
}

