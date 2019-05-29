package com.example.projekt_proz.activities.admin;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;

public class TestEditGeneralFragment extends Fragment {
    private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    private prozTest currentTest;

    private EditText inputTitle, inputDateStart, inputDateEnd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_edit_general, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentTest = ((TestEditActivity) getActivity()).currentTest;

        inputTitle = view.findViewById(R.id.text_input_question_name);
        inputTitle.setText(currentTest.getTitle());
        inputTitle.addTextChangedListener(new TextWatcher() {
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

        inputDateStart = view.findViewById(R.id.text_input_start_date);
        if (currentTest.getStartDate() != null)
            inputDateStart.setText(dateFormat.format(currentTest.getStartDate()));
        inputDateStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                    return;

                Calendar currentDate = Calendar.getInstance();
                if (currentTest.getStartDate() != null)
                    currentDate.setTimeInMillis(currentTest.getStartDate().getTime());
                final int currenYear = currentDate.get(Calendar.YEAR);
                final int currentMonth = currentDate.get(Calendar.MONTH);
                final int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
                final int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
                final int currentMinute = currentDate.get(Calendar.MINUTE);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, final int year, final int month, final int day) {
                        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, final int hour, final int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, month, day, hour, minute);
                                currentTest.setStartDate(new Timestamp(c.getTime().getTime()));
                                inputDateStart.setText(dateFormat.format(currentTest.getStartDate()));
                            }
                        }, currentHour, currentMinute, true);
                        timePicker.setTitle("Wybierz godzinę rozpoczęcia");
                        timePicker.show();
                    }
                }, currenYear, currentMonth, currentDay);
                datePicker.setTitle("Wybierz datę rozpoczęcia");
                datePicker.show();
            }
        });


        inputDateEnd = view.findViewById(R.id.text_input_end_date);
        if (currentTest.getEndDate() != null)
            inputDateEnd.setText(dateFormat.format(currentTest.getEndDate()));
        inputDateEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                    return;

                Calendar currentDate = Calendar.getInstance();
                if (currentTest.getEndDate() != null)
                    currentDate.setTimeInMillis(currentTest.getEndDate().getTime());
                final int currenYear = currentDate.get(Calendar.YEAR);
                final int currentMonth = currentDate.get(Calendar.MONTH);
                final int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
                final int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
                final int currentMinute = currentDate.get(Calendar.MINUTE);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, final int year, final int month, final int day) {
                        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, final int hour, final int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, month, day, hour, minute);
                                currentTest.setEndDate(new Timestamp(c.getTime().getTime()));
                                inputDateEnd.setText(dateFormat.format(currentTest.getEndDate()));
                            }
                        }, currentHour, currentMinute, true);
                        timePicker.setTitle("Wybierz godzinę zakończenia");
                        timePicker.show();
                    }
                }, currenYear, currentMonth, currentDay);
                datePicker.setTitle("Wybierz datę zakończenia");
                datePicker.show();
            }
        });
    }
}
