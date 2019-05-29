package com.example.projekt_proz.activities.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;

import okhttp3.Credentials;
import retrofit2.Response;

public class TestEditActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    prozTest currentTest;

    String login;
    String password;

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_test_edit);

        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            currentTest = (prozTest) getIntent().getSerializableExtra("test");
            if (currentTest == null) {
                // Creating a new test
                currentTest = new prozTest();
                currentTest.initQuestions(0);
            }
        } else {
            currentTest = (prozTest) savedInstanceState.getSerializable("test");
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("test", currentTest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.save_changes) {
            saveTest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setTitle("Zapisywanie zmian")
            .setMessage("Czy chcesz wyjść bez zapisywania zmian?")
            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    supportFinishAfterTransition();
                }

            })
            .setNegativeButton("Nie", null)
            .setCancelable(true)
            .show();
    }

    public void saveTest() {
        if (currentTest.getTitle() == null || currentTest.getTitle().length() == 0) {
            Toast.makeText(TestEditActivity.this, "Nie podano nazwy testu!", Toast.LENGTH_LONG).show();
            return;
        }
        if (currentTest.getStartDate() == null)
        {
            Toast.makeText(TestEditActivity.this, "Nie podano daty rozpoczęcia!", Toast.LENGTH_LONG).show();
            return;
        }
        if (currentTest.getEndDate() == null)
        {
            Toast.makeText(TestEditActivity.this, "Nie podano daty zakończenia!", Toast.LENGTH_LONG).show();
            return;
        }
        if (currentTest.getStartDate().getTime() > currentTest.getEndDate().getTime())
        {
            Toast.makeText(TestEditActivity.this, "Data rozpoczęcia jest późniejsza niż zakończenia!", Toast.LENGTH_LONG).show();
            return;
        }


        if (currentTest.getTestID() < 0)
        {
            new AddTest(login, password, currentTest).execute();
            return;
        }

        new AlertDialog.Builder(this)
            .setTitle("Test już zapisano")
            .setMessage("Spowoduje to utworzenie duplikatu. Czy na pewno chcesz to zrobić?")
            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new AddTest(login, password, currentTest).execute();
                }

            })
            .setNegativeButton("Nie", null)
            .setCancelable(true)
            .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.action_test_settings:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_test_questions:
                viewPager.setCurrentItem(1);
                break;
            case R.id.action_test_users:
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
        bottomNavigationView.getMenu().findItem(R.id.action_test_settings).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.action_test_questions).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.action_test_users).setChecked(false);

        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class AddTest extends AsyncTask<Void, Void, prozTest>
    {
        private final String login;
        private final String password;
        private final prozTest test;

        private ProgressDialog dialog;

        public AddTest(String login, String password, prozTest test) {
            super();
            this.login = login;
            this.password = password;
            this.test = test;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(TestEditActivity.this, "", "Zapisywanie testu", true);
        }

        @Override
        protected prozTest doInBackground(Void... voids) {
            try {
                return new MyClient().tests().addTest(test, Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(prozTest test) {
            dialog.cancel();
            if (test == null) {
                Toast.makeText(TestEditActivity.this, "Nie udało się zapisać testu!", Toast.LENGTH_LONG).show();
                return;
            }

            supportFinishAfterTransition();
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new TestEditGeneralFragment();
                case 1:
                    return new TestEditQuestionsFragment();
                case 2:
                    return new TestEditUsersFragment();
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
