package com.example.projekt_proz.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;

public class FetchQuestions extends AsyncTask<Void, Void, List<prozQuestion>> {
    private final Activity activity;
    private final String login;
    private final String password;
    private final ArrayList<prozQuestion> questionsList;
    private final RecyclerView listView;
    private final SwipeRefreshLayout refreshLayout;

    public FetchQuestions(Activity activity, String login, String password, ArrayList<prozQuestion> questionsList, RecyclerView listView, SwipeRefreshLayout refreshLayout) {
        this.activity = activity;
        this.login = login;
        this.password = password;
        this.questionsList = questionsList;
        this.listView = listView;
        this.refreshLayout = refreshLayout;
    }

    @Override
    protected void onPreExecute() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    protected List<prozQuestion> doInBackground(Void... voids) {
        try {
            return new MyClient().questions().listQuestions(Credentials.basic(login, password)).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<prozQuestion> testList) {
        refreshLayout.setRefreshing(false);
        if (testList == null) {
            Toast.makeText(activity, "Nie udało się pobrać pytań!", Toast.LENGTH_LONG).show();
            activity.finish();
            return;
        }
        this.questionsList.clear();
        this.questionsList.addAll(testList);
        this.listView.getAdapter().notifyDataSetChanged();
    }
}
