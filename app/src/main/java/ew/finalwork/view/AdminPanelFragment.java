package ew.finalwork.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ew.finalwork.R;
import ew.finalwork.viewmodel.MainViewModel;


public class AdminPanelFragment extends Fragment {

    MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_panel_fragment, container, false);
//        Button adder = v.findViewById(R.id.addTest);
//        adder.setOnClickListener(view -> {
//            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//            alert.setTitle("Input name");
//            EditText input = new EditText(getContext());
//            alert.setView(input);
//            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    final String value = input.getText().toString();
//
//                }
//            });
//
//            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    dialog.cancel();
//                }
//            });
//
//            alert.show();
//        });
//        RecyclerView mRecyclerView = v.findViewById(R.id.recyclerAdmin);
//        RecyclerView.LayoutManager mLayoutManager;
//        mLayoutManager = new LinearLayoutManager(this.getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        ref = reference.document("metadata");
//        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Map<String, Object> notParsedTest = document.getData();
//                        String arrayOfTests = notParsedTest.get("tests-array").toString();
//                        myDataset = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
//                        mAdapter = new AdminAdapter(myDataset);
//                        mRecyclerView.setAdapter(mAdapter);
//                    }
//                }
//            }
//        });
        return v;

    }
}
