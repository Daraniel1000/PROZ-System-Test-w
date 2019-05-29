package com.example.projekt_proz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminTestViewAdapter extends RecyclerView.Adapter<AdminTestViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private OnTestClickListener mOnTestClickListener;

    private List<prozTest> adminTestList;

    public interface OnTestClickListener {
        void onTestClick(CardView view, int position);
    }

    public AdminTestViewAdapter(Context ctx, ArrayList<prozTest> testList, OnTestClickListener OnTestClickListener) {
        inflater = LayoutInflater.from(ctx);
        adminTestList = testList;
        mOnTestClickListener = OnTestClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_recycler_item_test, parent, false);
        return new MyViewHolder(view, mOnTestClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTestViewAdapter.MyViewHolder holder, int position) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        holder.tvTitle.setText(adminTestList.get(position).getTitle());
        holder.tvStartDate.setText(dateFormat.format(adminTestList.get(position).getStartDate()));
        holder.tvEndDate.setText(dateFormat.format(adminTestList.get(position).getEndDate()));
    }

    @Override
    public int getItemCount() {
        return adminTestList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvTitle, tvStartDate, tvEndDate;

        MyViewHolder(View itemView, final OnTestClickListener OnTestClickListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnTestClickListener.onTestClick(cardView, getAdapterPosition());
                }
            });
        }
    }
}