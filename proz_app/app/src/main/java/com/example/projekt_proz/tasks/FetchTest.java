package com.example.projekt_proz.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.projekt_proz.activities.WithinTest;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;

import okhttp3.Credentials;

public class FetchTest extends AsyncTask<Void, Void, prozTest> {
    private final Activity activity;
    private final Class<? extends Activity> targetActivity;
    private final String login;
    private final String password;
    private final CardView card;
    private final int testId;

    private ProgressDialog dialog;

    public FetchTest(Activity activity, Class<? extends Activity> targetActivity, String login, String password, CardView card, int testId) {
        super();
        this.activity = activity;
        this.targetActivity = targetActivity;
        this.login = login;
        this.password = password;
        this.card = card;
        this.testId = testId;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(activity, "", "Ładowanie testu", true);
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
            Toast.makeText(activity, "Nie udało się załadować testu!", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(activity, targetActivity);
        intent.putExtra("login", login);
        intent.putExtra("password", password);
        intent.putExtra("test", test);
        ActivityOptionsCompat options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                card,
                "testActivity"
            );
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
