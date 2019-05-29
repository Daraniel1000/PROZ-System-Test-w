package com.example.projekt_proz.activities.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozUser;
import com.example.projekt_proz.models.prozTest;
import com.example.projekt_proz.net.MyClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Credentials;

public class TestEditUsersFragment extends Fragment {
    private String login;
    private String password;
    private List<Integer> usersToAdd;

    private RadioGroup radioGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_edit_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        usersToAdd = ((TestEditActivity) getActivity()).usersToAdd;
        login = ((TestEditActivity) getActivity()).login;
        password = ((TestEditActivity) getActivity()).password;

        radioGroup = view.findViewById(R.id.radio_group);

        new LoadUsers().execute();
    }

    private void updateUsers(List<prozUser> usersList) {
        radioGroup.removeAllViews();

        for(final prozUser user : usersList)
        {
            CheckBox button = new CheckBox(getActivity());
            button.setId(View.generateViewId());
            button.setText(user.getFirstName() + " " + user.getLastName() + " [" + user.getLogin() + "]");
            button.setChecked(usersToAdd.contains(user.getUserId()));
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        usersToAdd.add(user.getUserId());
                    else
                        usersToAdd.remove(user.getUserId());
                }
            });

            button.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1f));
            button.setBackgroundColor(getResources().getColor(R.color.colorTestQuestion));
            radioGroup.addView(button);
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
        protected void onPostExecute(List<prozUser> testList) {
            dialog.cancel();
            if (testList == null) {
                Toast.makeText(getActivity(), "Nie udało się pobrać użytkowników!", Toast.LENGTH_LONG).show();
                getActivity().finish();
                return;
            }
            updateUsers(testList);
        }
    }
}
