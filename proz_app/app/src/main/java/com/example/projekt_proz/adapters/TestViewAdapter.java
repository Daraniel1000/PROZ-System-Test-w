package com.example.projekt_proz.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
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

    private List<prozTest> testList = new ArrayList<>();

    public interface OnTestClickListener {
        void onTestClick(CardView view, int position);
    }

    public TestViewAdapter(Context ctx, OnTestClickListener onTestClickListener) {
        inflater = LayoutInflater.from(ctx);
        mOnTestClickListener = onTestClickListener;
    }

    public void setTestList(List<prozTest> testList) {
        this.testList = testList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, mOnTestClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestViewAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(testList.get(position).getTitle());
        holder.tvEndCountdown.setText(testList.get(position).getEndDate()); // TODO: odliczanie
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvTitle, tvEndCountdown;

        public MyViewHolder(View itemView, final OnTestClickListener onTestClickListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvEndCountdown = itemView.findViewById(R.id.tvEndCountdown);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTestClickListener.onTestClick(cardView, getAdapterPosition());
                }
            });
        }
    }
}