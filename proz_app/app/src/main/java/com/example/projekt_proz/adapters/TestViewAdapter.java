package com.example.projekt_proz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projekt_proz.R;
import com.example.projekt_proz.models.prozTest;

import java.util.ArrayList;
import java.util.Calendar;
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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view, mOnTestClickListener);
    }

    @Override
    public void onBindViewHolder(TestViewAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(testList.get(position).getTitle());

        long startTime = testList.get(position).getStartDate().getTime();
        long endTime = testList.get(position).getEndDate().getTime();
        long currentTime = Calendar.getInstance().getTime().getTime();
        if (currentTime < startTime)
        {
            holder.tvEndLabel.setText("Rozpocznie się: ");
            holder.tvEndCountdown.setText(DateUtils.getRelativeTimeSpanString(startTime, currentTime, 0, DateUtils.FORMAT_ABBREV_RELATIVE));
        }
        else
        {
            holder.tvEndLabel.setText(currentTime < endTime ? "Kończy się: " : "Zakończył się: ");
            holder.tvEndCountdown.setText(DateUtils.getRelativeTimeSpanString(endTime, currentTime, 0, DateUtils.FORMAT_ABBREV_RELATIVE));
        }

        if (testList.get(position).isFinished())
        {
            holder.tvReadyToStartLabel.setText("Wyniki >");
        }
        else
        {
            if (currentTime >= startTime && currentTime <= endTime)
            {
                holder.tvReadyToStartLabel.setText("Rozpocznij >"); // TODO: inne tło żeby wyróżnić
            }
            else
            {
                holder.tvReadyToStartLabel.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvTitle, tvReadyToStartLabel, tvEndLabel, tvEndCountdown;

        MyViewHolder(View itemView, final OnTestClickListener onTestClickListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvReadyToStartLabel = itemView.findViewById(R.id.tvReadyToStartLabel);
            tvEndLabel = itemView.findViewById(R.id.tvEndLabel);
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