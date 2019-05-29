package com.example.projekt_proz.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;

import okhttp3.Credentials;

public class FetchQuestion extends AsyncTask<Void, Void, prozQuestion> {
    private final Activity activity;
    private final Class<? extends Activity> targetActivity;
    private final String login;
    private final String password;
    private final CardView card;
    private final int questionId;

    private ProgressDialog dialog;

    public FetchQuestion(Activity activity, Class<? extends Activity> targetActivity, String login, String password, CardView card, int questionId) {
        super();
        this.activity = activity;
        this.targetActivity = targetActivity;
        this.login = login;
        this.password = password;
        this.card = card;
        this.questionId = questionId;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(activity, "", "Ładowanie pytania", true);
    }

    @Override
    protected prozQuestion doInBackground(Void... voids) {
        try {
            return new MyClient().questions().getQuestion(questionId, Credentials.basic(login, password)).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(prozQuestion question) {
        dialog.cancel();
        if (question == null) {
            Toast.makeText(activity, "Nie udało się załadować pytania!", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(activity, targetActivity);
        intent.putExtra("login", login);
        intent.putExtra("password", password);
        intent.putExtra("question", question);
        ActivityOptionsCompat options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                card,
                "testActivity"
            );
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
