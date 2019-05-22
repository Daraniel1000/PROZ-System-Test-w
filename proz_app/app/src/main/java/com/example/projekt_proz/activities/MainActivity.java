package com.example.projekt_proz.activities;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;

import okhttp3.Credentials;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AfterLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logInService(View view) {
        String account = ((EditText) findViewById(R.id.input1)).getText().toString();
        String password = ((EditText) findViewById(R.id.input2)).getText().toString();

        new LoginTask(account, password).execute();
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String login;
        private final String password;
        private ProgressDialog dialog;

        LoginTask(String login, String password) {
            super();
            this.login = login;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this, "", "Logowanie", true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                return new MyClient().tests().getAvailableTests(Credentials.basic(login, password)).execute().isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            dialog.cancel();
            if (success) {
                Intent intent = new Intent(MainActivity.this, AfterLogin.class);
                intent.putExtra("login", login);
                intent.putExtra("password", password);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Nie udało się zalogować!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
