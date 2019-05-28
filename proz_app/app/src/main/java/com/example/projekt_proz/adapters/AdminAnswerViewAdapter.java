package com.example.projekt_proz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozAnswer;

import java.util.List;

public class AdminAnswerViewAdapter extends RecyclerView.Adapter<AdminAnswerViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<prozAnswer> answerList;

    public AdminAnswerViewAdapter(Context ctx, List<prozAnswer> answerList) {
        inflater = LayoutInflater.from(ctx);
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.a_recycler_item_answer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminAnswerViewAdapter.MyViewHolder holder, int position) {
        final prozAnswer answer = answerList.get(position);

        holder.teAnswer.setText(answer.getText());
        holder.cbCorrect.setChecked(answer.isCorrect());

        holder.teAnswerChangeListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                answer.setText(editable.toString());
            }
        };
        holder.teAnswer.addTextChangedListener(holder.teAnswerChangeListener);

        holder.cbCorrect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                answer.setCorrect(b);
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        holder.cbCorrect.setOnCheckedChangeListener(null);
        holder.teAnswer.removeTextChangedListener(holder.teAnswerChangeListener);
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextWatcher teAnswerChangeListener;
        private CheckBox cbCorrect;
        private EditText teAnswer;
        MyViewHolder(View itemView) {
            super(itemView);
            cbCorrect = itemView.findViewById(R.id.cbCorrect);
            teAnswer = itemView.findViewById(R.id.teAnswer);
        }
    }
}