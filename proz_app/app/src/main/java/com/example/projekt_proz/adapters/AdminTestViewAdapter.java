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

import java.util.ArrayList;
import java.util.List;

public class AdminTestViewAdapter extends RecyclerView.Adapter<AdminTestViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private OnAdminTestClickListener mOnAdminTestClickListener;

    private List<prozTest> adminTestList = new ArrayList<>();

    public interface OnAdminTestClickListener {
        void onAdminTestClick(CardView view, int position);
    }

    public AdminTestViewAdapter(Context ctx, OnAdminTestClickListener OnAdminTestClickListener) {
        inflater = LayoutInflater.from(ctx);
        mOnAdminTestClickListener = OnAdminTestClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_recycler_item_test_admin_view, parent, false);
        return new MyViewHolder(view, mOnAdminTestClickListener);
    }

    @Override
    public void onBindViewHolder(AdminTestViewAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(adminTestList.get(position).getTitle());
        holder.tvQuestionCount.setText(""+adminTestList.get(position).getQuestionsSize());
        holder.tvID.setText(""+adminTestList.get(position).getTestID());


    }

    public void setAdminTestList(List<prozTest> adminTestList) {
        this.adminTestList=adminTestList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return adminTestList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvTitle, tvQuestionCount,tvID;

        MyViewHolder(View itemView, final OnAdminTestClickListener OnAdminTestClickListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvID = itemView.findViewById(R.id.tvID);
            tvQuestionCount = itemView.findViewById(R.id.tvQuestionNumber);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnAdminTestClickListener.onAdminTestClick(cardView, getAdapterPosition());
                }
            });
        }
    }
}