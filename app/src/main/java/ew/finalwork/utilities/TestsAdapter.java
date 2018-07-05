package ew.finalwork.utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.ViewHolder> {
    private ArrayList<String> dataSource;
    private static ClickerListener clicker;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setTextSize(40);
            textView.setOnClickListener(this);
            textView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clicker.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clicker.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }

    public TestsAdapter(ArrayList<String> dataArgs) {
        dataSource = dataArgs;
    }

    @NonNull
    @Override
    public TestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new TextView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface ClickerListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(ClickerListener clickerListener) {
        TestsAdapter.clicker = clickerListener;
    }

    public void addTest(String testName){
        dataSource.add(testName);
    }

    public void setTests(ArrayList<String> tests){
        dataSource = tests;
    }
}