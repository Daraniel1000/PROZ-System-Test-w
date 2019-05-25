package com.example.projekt_proz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozAnswer;

import java.util.ArrayList;
import java.util.List;

public class AnswerViewAdapter extends RecyclerView.Adapter<AnswerViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;


    private List<prozAnswer> answerList = new ArrayList<>();


    public AnswerViewAdapter(Context ctx) {
        inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_recycler_item_answer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerViewAdapter.MyViewHolder holder, int position) {
        holder.teAnswer.setText(answerList.get(position).getText());
        holder.cbCorrect.setChecked(answerList.get(position).isCorrect());
        holder.cardView.setId(position+300);
        Log.d("XD:","text,checkbox: "+ holder.teAnswer.getId()+", "+holder.cbCorrect.getId());


    }

    public void setAnswerList(List<prozAnswer> answerList, boolean ins, boolean del) {
        this.answerList=answerList;
       if(ins)notifyItemInserted(answerList.size()-1);
       else if(del){notifyItemRemoved(answerList.size());}

    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbCorrect;
        private EditText teAnswer;
        private CardView cardView;
        MyViewHolder(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card_view);
            cbCorrect = itemView.findViewById(R.id.cbCorrect);
            teAnswer = itemView.findViewById(R.id.teAnswer);


        }
    }
}