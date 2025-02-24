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
import com.example.projekt_proz.models.prozQuestion;

import java.util.ArrayList;
import java.util.List;

public class AdminQuestionsViewAdapter extends RecyclerView.Adapter<AdminQuestionsViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private OnQuestionClickListener mOnQuestionClickListener;

    private List<prozQuestion> questionList;

    public interface OnQuestionClickListener {
        void onQuestionClick(CardView view, int position);
    }

    public AdminQuestionsViewAdapter(Context ctx, ArrayList<prozQuestion> questionList, OnQuestionClickListener OnQuestionClickListener) {
        inflater = LayoutInflater.from(ctx);
        this.questionList = questionList;
        mOnQuestionClickListener = OnQuestionClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_recycler_item_question, parent, false);
        return new MyViewHolder(view, mOnQuestionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminQuestionsViewAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(questionList.get(position).getText());
        holder.tvQuestionType.setText(questionList.get(position).getType() == prozQuestion.TYPE_SINGLE_CHOICE ? "Pytanie jednokrotnego wyboru" : "Pytanie wielokrotnego wyboru");
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvTitle, tvQuestionType;

        MyViewHolder(View itemView, final OnQuestionClickListener OnQuestionClickListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvQuestionType = itemView.findViewById(R.id.tvQuestionType);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnQuestionClickListener.onQuestionClick(cardView, getAdapterPosition());
                }
            });
        }
    }
}