package com.example.projekt_proz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;
import java.util.List;

public class TestViewAdapter extends RecyclerView.Adapter<TestViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private OnTestClickListener mOnTestClickListener;

    private List<prozTest> prozTestArrayList;

    public TestViewAdapter(Context ctx, List<prozTest> prozTestArrayList, OnTestClickListener onTestClickListener) {

        inflater = LayoutInflater.from(ctx);
        this.prozTestArrayList = prozTestArrayList;
        mOnTestClickListener = onTestClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, mOnTestClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestViewAdapter.MyViewHolder holder, int position) {

        holder.tvTitle.setText(prozTestArrayList.get(position).getTitle());
        holder.tvStartD.setText(prozTestArrayList.get(position).getStartDate());
        holder.tvEndD.setText(prozTestArrayList.get(position).getEndDate());
        // holder.tvQAmount.setText(""+prozTestArrayList.get(position).getQuestionsSize());// TODO
    }

    @Override
    public int getItemCount() {
        return prozTestArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvStartD, tvEndD, tvQAmount;
        OnTestClickListener onTestClickListener;

        public MyViewHolder(View itemView, OnTestClickListener onTestClickListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStartD = itemView.findViewById(R.id.tvStartD);
            tvEndD = itemView.findViewById(R.id.tvEndD);
            tvQAmount = itemView.findViewById(R.id.tvQAmount);

            this.onTestClickListener = onTestClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTestClickListener.onTestClick(getAdapterPosition());
        }
    }

    public interface OnTestClickListener {
        void onTestClick(int position);
    }
}