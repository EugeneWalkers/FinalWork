package ew.finalwork.utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ew.finalwork.R;
import ew.finalwork.model.TestResult;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    public void setResults( ArrayList<TestResult> results) {
        this.results = results;
    }

    ArrayList<TestResult> results;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView myCard;
        TextView test;
        TextView result;

        ViewHolder(View itemView) {
            super(itemView);
            myCard = itemView.findViewById(R.id.card);
            test = myCard.findViewById(R.id.testNameInResults);
            result = myCard.findViewById(R.id.resultOfTheTest);
        }
    }

    public ResultsAdapter(ArrayList<TestResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder holder, int position) {
            holder.test.setText(results.get(position).getTestName());
            holder.result.setText(results.get(position).getTestResult());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
