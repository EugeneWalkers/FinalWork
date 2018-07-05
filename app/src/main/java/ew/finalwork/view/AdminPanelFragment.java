//package ew.finalwork.view;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.Button;
//
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//
//public class AdminPanelFragment extends Fragment {
//
//    String[] myDataset;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference reference = db.collection("tests");
//    DocumentReference ref;
//    String TAG = "TestAccessor";
//    RecyclerView.Adapter mAdapter;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.admin_panel_fragment, container, false);
//        Button adder = v.findViewById(R.id.addTest);
//        adder.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                alert.setTitle("Input name");
//
//// Set an EditText view to get user input
//                final EditText input = new EditText(getContext());
//                alert.setView(input);
//
//                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        final String value = input.getText().toString();
//                        reference.document("metadata").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    DocumentSnapshot document = task.getResult();
//                                    if (document.exists()) {
//                                        ArrayList<String> tests = (ArrayList<String>)document.get("tests-array");
//                                        tests.add(value);
//                                        reference.document("metadata").update("tests-array", tests);
//                                        reference.document("metadata").update("testsnumber", tests.size());
//                                        Map<String, Object> map = new HashMap<>();
//                                        map.put("name", value);
//                                        reference.document(value).set(map);
//                                        Intent intent = new Intent(getActivity(), AdminActivity.class);
//                                        intent.putExtra(MainActivity.TEST_NAME, value);
//                                        startActivity(intent);
//                                    }
//                                }
//
//                            }
//                        });
//                    }
//                });
//
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.cancel();
//                    }
//                });
//
//                alert.show();
//            }
//        });
//        final RecyclerView mRecyclerView = v.findViewById(R.id.recyclerAdmin);
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
//                        Log.i(TAG, "Document exists!");
//                        Map<String, Object> notParsedTest = document.getData();
//                        String arrayOfTests = notParsedTest.get("tests-array").toString();
//                        myDataset  = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
//                        mAdapter = new AdminAdapter(myDataset);
//                        mRecyclerView.setAdapter(mAdapter);
//                    }
//                }
//            }
//        });
//        return v;
//    }
//}
