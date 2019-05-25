package com.example.projekt_proz.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import com.example.projekt_proz.models.prozAnswer;

import java.util.ArrayList;
import java.util.List;

public class AdminAnswerViewAdapter extends RecyclerView.Adapter<AdminAnswerViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;


    private List<prozAnswer> answerList = new ArrayList<>();


    public AdminAnswerViewAdapter(Context ctx) {
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_recycler_item_answer_admin_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminAnswerViewAdapter.MyViewHolder holder, int position) {
        holder.tvText.setText(answerList.get(position).getText());
       if(answerList.get(position).isCorrect())holder.cardView.setBackgroundColor(Color.parseColor("#1B5E20"));
        else
                holder.cardView.setBackgroundColor(Color.parseColor("#B71C1C"));


    }

    public void setAnswerList(List<prozAnswer> answerList) {
        this.answerList=answerList;


    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvText;
        private CardView cardView;
        MyViewHolder(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card_view);
            tvText = itemView.findViewById(R.id.tvText);


        }
    }
}