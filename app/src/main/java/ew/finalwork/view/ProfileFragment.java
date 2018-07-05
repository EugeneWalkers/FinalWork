package ew.finalwork.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ew.finalwork.R;
import ew.finalwork.model.User;
import ew.finalwork.viewmodel.MainViewModel;

public class ProfileFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = MainActivity.obtainViewModel(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mRecyclerView = v.findViewById(R.id.userTests);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        Observer<User> userObserver = user -> {
            ((TextView) v.findViewById(R.id.name)).setText(user.getUser().getEmail());
            ((TextView) v.findViewById(R.id.type)).setText(user.getType());
            mRecyclerView.setAdapter(viewModel.getResultsAdapterInstance());
        };
        viewModel.getUser().observe(this, userObserver);
        return v;
    }


}
// user: test1:0.5, test3:1