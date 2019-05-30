package com.example.projekt_proz.activities.admin;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozAnswer;
import com.example.projekt_proz.models.prozQuestion;
import com.example.projekt_proz.models.prozResults;
import com.example.projekt_proz.models.prozTest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ResultsQuestionStatsFragment extends Fragment {
    private prozTest currentTest;
    private ArrayList<prozResults> pResults;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results_question_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentTest = ((ResultsActivity) getActivity()).currentTest;
        pResults = ((ResultsActivity) getActivity()).pResults;

        BarChart chart = view.findViewById(R.id.chart);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);

        chart.getAxisRight().setEnabled(false);

        int[] points = new int[currentTest.getQuestionsSize()];
        for(prozResults results : pResults)
        {
            for (int i = 0; i < currentTest.getQuestionsSize(); i++)
            {
                prozQuestion question = currentTest.getQuestion(i);
                boolean correct = true;
                for(prozAnswer a : question.getAnswers())
                {
                    if (a.isCorrect() != results.getAnswerID().contains(a.getAnswerID()))
                    {
                        correct = false;
                        break;
                    }
                }
                if (correct)
                    points[i] ++;
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i < points.length; i++)
            entries.add(new BarEntry(i + 1, points[i]));

        BarDataSet dataSet = new BarDataSet(entries, "Punkty");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.invalidate();
    }
}
