package ew.finalwork.view;

import android.support.v7.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

//    String[] myDataset;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference reference = db.collection("tests");
//    DocumentReference ref;
//    RecyclerView.Adapter mAdapter;
//    String testName;
//    Button addQuestion;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin);
//        final RecyclerView mRecyclerView = findViewById(R.id.recyclerQuestions);
//        Intent intent = getIntent();
//        testName = intent.getStringExtra(MainActivity.TEST_NAME);
//        addQuestion = findViewById(R.id.addQuestion);
//        addQuestion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ref = reference.document(testName);
//                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Map<String, Object> notParsedTest = document.getData();
//                                Intent goToChangeQuestionActivity = new Intent(AdminActivity.this, ChangeQuestionActivity.class);
//                                if (notParsedTest.containsKey("questions")){
//                                    String arrayOfTests = notParsedTest.get("questions").toString();
//                                    myDataset = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
//                                    goToChangeQuestionActivity.putExtra(MainActivity.QUESTIONS, myDataset);
//                                }
//                                goToChangeQuestionActivity.putExtra(MainActivity.TEST_NAME, testName);
//                                startActivity(goToChangeQuestionActivity);
//                                finish();
//                            }
//                        }
//                    }
//                });
//
//
//            }
//        });
//        RecyclerView.LayoutManager mLayoutManager;
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        ref = reference.document(testName);
//        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Map<String, Object> notParsedTest = document.getData();
//                        if (document.contains("questions")){
//                            String arrayOfTests = notParsedTest.get("questions").toString();
//                            String[] notParcedQuestions = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
//                            myDataset = new String[notParcedQuestions.length];
//                            for (int i = 0; i < myDataset.length; i++) {
//                                String[] someQ = notParcedQuestions[i].split(":");
//                                myDataset[i] = someQ[0];
//                            }
//                        }
//                        else{
//                            myDataset = new String[0];
//                        }
//                        mAdapter = new QuestionsToChangeAdapter(myDataset, testName);
//                        mRecyclerView.setAdapter(mAdapter);
//
//                    }
//                }
//            }
//        });
//    }
}
