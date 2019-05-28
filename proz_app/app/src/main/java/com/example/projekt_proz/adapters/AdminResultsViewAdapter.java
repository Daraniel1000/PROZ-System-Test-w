package com.example.projekt_proz.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projekt_proz.R;
import com.example.projekt_proz.activities.admin.FragmentAdminResultsList;
import com.example.projekt_proz.models.prozResults;

import java.util.ArrayList;
import java.util.List;

public class AdminResultsViewAdapter extends RecyclerView.Adapter<AdminResultsViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;

    private List<prozResults> results;

    public AdminResultsViewAdapter(Context ctx, ArrayList<prozResults> results) {
        inflater = LayoutInflater.from(ctx);
        this.results = results;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_recycler_item_result_admin_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminResultsViewAdapter.MyViewHolder holder, int position) {
        holder.tvresultId.setText(""+results.get(position).getResultsID());
        holder.tvuserId.setText(""+results.get(position).getUserID());
        holder.tvPoints.setText(""+results.get(position).getPoints());
        holder.tvtestId.setText(""+results.get(position).getTestID());
        holder.tvTIME.setText(results.get(position).getSentDate().toString());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvresultId, tvuserId, tvtestId, tvPoints, tvTIME;

        MyViewHolder(View itemView) {
            super(itemView);
            tvresultId = itemView.findViewById(R.id.tvresultID);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvtestId = itemView.findViewById(R.id.tvtestID);
            tvuserId = itemView.findViewById(R.id.tvuserID);
            tvTIME = itemView.findViewById(R.id.tvTIME);
        }
    }
}