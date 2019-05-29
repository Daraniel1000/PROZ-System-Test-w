package com.example.projekt_proz.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.projekt_proz.adapters.TestViewAdapter;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;

public class FetchTests extends AsyncTask<Void, Void, List<prozTest>> {
    private final Activity activity;
    private final String login;
    private final String password;
    private final ArrayList<prozTest> testList;
    private final RecyclerView listView;
    private final SwipeRefreshLayout refreshLayout;

    public FetchTests(Activity activity, String login, String password, ArrayList<prozTest> testList, RecyclerView listView, SwipeRefreshLayout refreshLayout) {
        this.activity = activity;
        this.login = login;
        this.password = password;
        this.testList = testList;
        this.listView = listView;
        this.refreshLayout = refreshLayout;
    }

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
            Toast.makeText(activity, "Nie udało się pobrać testów!", Toast.LENGTH_LONG).show();
            activity.finish();
            return;
        }
        this.testList.clear();
        this.testList.addAll(testList);
        this.listView.getAdapter().notifyDataSetChanged();
    }
}
