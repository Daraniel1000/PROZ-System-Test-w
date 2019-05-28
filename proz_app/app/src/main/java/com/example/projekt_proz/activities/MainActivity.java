package com.example.projekt_proz.activities;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.activities.admin.AdminPanelActivity;
import com.example.projekt_proz.models.prozUser;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;

import okhttp3.Credentials;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public EditText editLogin, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editLogin = findViewById(R.id.input1);
        editPassword = findViewById(R.id.input2);

        if (savedInstanceState != null)
        {
            editLogin.setText(savedInstanceState.getString("login"));
            editPassword.setText(savedInstanceState.getString("password"));
        }
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
        outState.putString("login", editLogin.getText().toString());
        outState.putString("password", editPassword.getText().toString());
    }

    public void logInService(View view) {
        String account = editLogin.getText().toString();
        String password = editPassword.getText().toString();
        new LoginTask(view, account, password).execute();
    }

    private class LoginTask extends AsyncTask<Void, Void, prozUser> {
        private final View loginButton;
        private final String login;
        private final String password;
        private ProgressDialog dialog;

        LoginTask(View loginButton, String login, String password) {
            super();
            this.loginButton = loginButton;
            this.login = login;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "", "Logowanie", true);
        }

        @Override
        protected prozUser doInBackground(Void... voids) {
            try {
                return new MyClient().users().getMe(Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(prozUser user) {
            dialog.cancel();
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, user.isAdmin() ? AdminPanelActivity.class : AfterLogin.class);
                intent.putExtra("login", login);
                intent.putExtra("password", password);

                ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        loginButton,
                        "testListActivity"
                    );
                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
            } else {
                Toast.makeText(MainActivity.this, "Nie udało się zalogować!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
