package ew.finalwork.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ew.finalwork.R;
import ew.finalwork.model.Test;
import ew.finalwork.utilities.TestsAdapter;
import ew.finalwork.viewmodel.MainViewModel;

public class TestsFragment extends Fragment {

    MainViewModel viewModel;
    public static final String TEST = "test";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = MainActivity.obtainViewModel(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tests, container, false);
        RecyclerView mRecyclerView = v.findViewById(R.id.recycler);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        TestsAdapter adapter = viewModel.getTestsAdapterInstance();
        adapter.setOnItemClickListener(new TestsAdapter.ClickerListener() {

            @Override
            public void onItemClick(View view, int position) {
                String testName = ((TextView) view).getText().toString();
                Intent goToTestActivity = new Intent(getActivity(), TestViewActivity.class);
                Test test = null;
                for (int i = 0; i < viewModel.getTests().getValue().size(); i++) {
                    if (testName.equals(viewModel.getTests().getValue().get(i).getTestName())){
                        test = viewModel.getTests().getValue().get(i);
                        break;
                    }
                }
                goToTestActivity.putExtra(TestsFragment.TEST, test);
                getActivity().startActivityForResult(goToTestActivity, MainActivity.REQUEST_RESULT);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        Observer<ArrayList<Test>> observerTests = tests -> {
            ArrayList<String> names = new ArrayList<>();
            for (Test test : tests) {
                names.add(test.getTestName());
            }
            adapter.setTests(names);
            mRecyclerView.setAdapter(adapter);
        };
        viewModel.getTests().observe(this, observerTests);
        return v;
    }

}
