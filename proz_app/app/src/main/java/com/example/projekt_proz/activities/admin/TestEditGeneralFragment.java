package com.example.projekt_proz.activities.admin;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozTest;

public class TestEditGeneralFragment extends Fragment {
    private prozTest currentTest;

    private EditText editText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_edit_general, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentTest = ((TestEditActivity) getActivity()).currentTest;

        // TODO: data startu/końca

        editText = view.findViewById(R.id.text_input_question_name);
        editText.setText(currentTest.getTitle());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentTest.setTitle(editable.toString());
            }
        });
    }
}
