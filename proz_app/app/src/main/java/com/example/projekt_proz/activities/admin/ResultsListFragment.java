package com.example.projekt_proz.activities.admin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.activities.WithinTest;
import com.example.projekt_proz.adapters.AdminResultsViewAdapter;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.models.prozUser;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.SortingOrder;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import okhttp3.Credentials;

public class ResultsListFragment extends Fragment implements TableDataClickListener<prozResults> {
    private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    private prozTest currentTest;
    private ArrayList<prozResults> pResults;
    private String login, password;

    private SortableTableView tableView;
    private ArrayList<prozUser> userList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        login = ((ResultsActivity) getActivity()).login;
        password = ((ResultsActivity) getActivity()).password;

        currentTest = ((ResultsActivity) getActivity()).currentTest;
        pResults = ((ResultsActivity) getActivity()).pResults;

        tableView = view.findViewById(R.id.tableView);
        tableView.setColumnCount(3);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(3);
        columnModel.setColumnWeight(0, 3);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 1);
        tableView.setColumnModel(columnModel);

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(), "Użytkownik", "Czas", "Pkt"));
        tableView.setColumnComparator(0, new SortbyUserID());
        tableView.setColumnComparator(1, new SortbyTIME());
        tableView.setColumnComparator(2, new SortbyPoints());
        tableView.setDataAdapter(new ResultsTableDataAdapter(getActivity(), pResults));
        tableView.sort(1, SortingOrder.ASCENDING);

        tableView.addDataClickListener(this);

        if (savedInstanceState != null)
        {
            userList = (ArrayList<prozUser>) savedInstanceState.getSerializable("userList");
        }
        else
        {
            new LoadUsers().execute();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("userList", userList);
    }

    @Override
    public void onDataClicked(int rowIndex, prozResults clickedData) {
        Intent i = new Intent(getActivity(), WithinTest.class);
        i.putExtra("test", currentTest);
        i.putExtra("results", clickedData);
        startActivity(i);
    }

    class SortbyUserID implements Comparator<prozResults> {
        public int compare(prozResults a, prozResults b) {
            return a.getUserID() - b.getUserID();
        }
    }

    class SortbyTIME implements Comparator<prozResults> {
        public int compare(prozResults a, prozResults b) {
            return a.getSentDate().compareTo(b.getSentDate());
        }
    }

    class SortbyPoints implements Comparator<prozResults> {
        public int compare(prozResults a, prozResults b) {
            return a.getPoints() - b.getPoints();
        }
    }

    public class ResultsTableDataAdapter extends TableDataAdapter<prozResults> {
        ResultsTableDataAdapter(Context context, List<prozResults> data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            prozResults results = getRowData(rowIndex);

            final TextView textView = new TextView(getContext());
            textView.setPadding(20, 15, 20, 15);
            textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
            textView.setTextSize(14);
            textView.setTextColor(0x99000000);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);

            switch (columnIndex) {
                case 0:
                    String userDisplayName = results.getUserID() + "?";
                    for(prozUser user : userList)
                    {
                        if (user.getUserId() == results.getUserID())
                        {
                            userDisplayName = user.getFirstName() + " " + user.getLastName() + " [" + user.getLogin() + "]";
                            break;
                        }
                    }
                    textView.setText(userDisplayName);
                    break;
                case 1:
                    textView.setText(dateFormat.format(results.getSentDate()));
                    break;
                case 2:
                    textView.setText(String.valueOf(results.getPoints()));
                    break;
            }

            return textView;
        }
    }

    public class LoadUsers extends AsyncTask<Void, Void, List<prozUser>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "", "Ładowanie użytkowników", true);
        }

        @Override
        protected List<prozUser> doInBackground(Void... voids) {
            try {
                return new MyClient().users().getAllUsers(Credentials.basic(login, password)).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<prozUser> userList) {
            dialog.cancel();
            if (userList == null) {
                Toast.makeText(getActivity(), "Nie udało się pobrać użytkowników!", Toast.LENGTH_LONG).show();
                getActivity().finish();
                return;
            }
            ResultsListFragment.this.userList.clear();
            ResultsListFragment.this.userList.addAll(userList);
            ResultsListFragment.this.tableView.getDataAdapter().notifyDataSetChanged();
        }
    }
}
