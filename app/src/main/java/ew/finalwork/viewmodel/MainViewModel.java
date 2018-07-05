package ew.finalwork.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import ew.finalwork.model.Question;
import ew.finalwork.model.Test;
import ew.finalwork.model.TestResult;
import ew.finalwork.model.User;
import ew.finalwork.utilities.DataUtility;
import ew.finalwork.utilities.ResultsAdapter;
import ew.finalwork.utilities.TestsAdapter;

public class MainViewModel extends ViewModel implements NavigationView.OnNavigationItemSelectedListener {

    private MutableLiveData<MenuItem> itemMutableLiveData;
    private MutableLiveData<User> user;
    private ResultsAdapter resultsAdapter;
    private TestsAdapter testsAdapter;

    public LiveData<ArrayList<Test>> getTests() {
        return tests;
    }

    private MutableLiveData<ArrayList<Test>> tests;
    private FirebaseFirestore dataBase;


    public ResultsAdapter getResultsAdapterInstance(){
        if (resultsAdapter == null){
            resultsAdapter = new ResultsAdapter(user.getValue().getResults());
        }
        resultsAdapter.setResults(user.getValue().getResults());
        return resultsAdapter;
    }

    public TestsAdapter getTestsAdapterInstance(){
        if (testsAdapter == null){
            ArrayList<String> names = new ArrayList<>();
            for (Test test:tests.getValue()){{
                names.add(test.getTestName());
            }}
            testsAdapter = new TestsAdapter(names);
        }
        return testsAdapter;
    }

    public MainViewModel() {
        super();
        itemMutableLiveData = new MutableLiveData<>();
        user = new MutableLiveData<>();
        tests = new MutableLiveData<>();
        tests.postValue(new ArrayList<>());
        dataBase = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        itemMutableLiveData.postValue(item);
        return true;
    }

    public LiveData<MenuItem> getItemLiveData() {
        return itemMutableLiveData;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void refreshResults(){
        dataBase.collection(DataUtility.USERS).document(user.getValue().getUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Map<String, Object> fireuser = document.getData();
                        ArrayList<TestResult> results = new ArrayList<>();
                        if (fireuser.containsKey(DataUtility.RESULTS)){

                            ArrayList<String> notParcedResults = (ArrayList<String>)fireuser.get(DataUtility.RESULTS);
                            for (String result:notParcedResults){
                                String[] parcedResult = result.split(":");
                                results.add(new TestResult(parcedResult[0], parcedResult[1]));
                            }
                        }
                        changeResults(results);
                    }
                }
            }

        });
    }

    public void setUser(User user){
        this.user.postValue(user);
    }

    public void changeResults(@NonNull ArrayList<TestResult> newResults){
        User user = this.user.getValue();
        if (user != null){
            user.setResults(newResults);
            this.user.postValue(user);
        }
    }

    public void addResults(TestResult result){
        this.user.getValue().addResult(result);
        this.user.postValue(this.user.getValue());
        dataBase.collection(DataUtility.USERS).document(user.getValue().getUser().getEmail()).update(DataUtility.RESULTS, user.getValue().getStringResults());
    }

    public void refreshTestsList(){
        tests.postValue(new ArrayList<>());
        dataBase.collection(DataUtility.TESTS).document(DataUtility.METADATA).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> notParsedTest = document.getData();
                    ArrayList<DocumentReference> references = (ArrayList<DocumentReference>)notParsedTest.get(DataUtility.TESTS);
                    for (int i=0; i<references.size(); i++){
                        references.get(i).get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){
                                DocumentSnapshot document1 = task1.getResult();
                                if (document1.exists()){
                                    ArrayList<Question> questions = new ArrayList<>();
                                    ArrayList<String> notParcedQuestions = (ArrayList<String>)document1.get(DataUtility.QUESTIONS);
                                    if (notParcedQuestions != null) {
                                        for (String s:notParcedQuestions){
                                            questions.add(new Question(s));
                                        }
                                    }
                                    String testName = document1.get(DataUtility.TEST_NAME).toString();
                                    Test test = new Test(questions, testName);
                                    addTest(test);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void addTest(Test test){
        tests.getValue().add(test);
        tests.postValue(tests.getValue());
    }
}
