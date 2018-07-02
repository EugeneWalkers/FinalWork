package ew.finalwork.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ew.finalwork.model.User;
import ew.finalwork.utilities.DataUtility;

public class LoginViewModel extends ViewModel {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private MutableLiveData<String> userState = new MutableLiveData<>();
    private User user;
    public static final String LOGIN_VIEW_MODEL_TAG = "login_view_model_tag";
    public static final String NOT_SIGNED = "not signed";
    public static final String SIGNING = "signing";
    public static final String ERROR_SIGNIN = "error";
    public static final String SUCCESS_SIGNIN = "success";

    String login, password;

    public LoginViewModel() {
        super();
        mAuth = FirebaseAuth.getInstance();
        user = null;
        userState.postValue(NOT_SIGNED);
    }

    public void readUserFromBase(@NonNull FirebaseUser firebaseUser) {
        db = FirebaseFirestore.getInstance();
        String email = firebaseUser.getEmail();
        if (!SIGNING.equals(userState.getValue())) {
            userState.postValue(SIGNING);
        }
        DocumentReference dataReader = db.collection(DataUtility.USERS).document(email);
        dataReader.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> data = document.getData();
                    String type = data.get("type").toString();
                    Map<String, String> results = new HashMap<>();
                    if (data.containsKey("results")) {
                        ArrayList<String> notParcedResults = (ArrayList) data.get("results");
                        for (int i = 0; i < notParcedResults.size(); i++) {
                            String[] result = notParcedResults.get(i).split(":");
                            results.put(result[0], result[1]);
                        }
                    }
                    user = new User(firebaseUser, type, results);
                    userState.postValue(SUCCESS_SIGNIN);
                } else {
                    userState.postValue(ERROR_SIGNIN);
                    //userState.postValue(NOT_SIGNED);
                }
            } else {
                userState.postValue(ERROR_SIGNIN);
                //userState.postValue(NOT_SIGNED);
            }
        }).addOnFailureListener(task -> {
            userState.postValue(ERROR_SIGNIN);
            //userState.postValue(NOT_SIGNED);
        });
    }

    public void onLoginClicked(String login, String password, Activity activity) {
        this.login = login;
        this.password = password;
        userState.postValue(SIGNING);
        mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(activity, task -> {
            Log.i(LOGIN_VIEW_MODEL_TAG, "entering...");
            if (task.isSuccessful()) {
                FirebaseUser fireuser = mAuth.getCurrentUser();
                readUserFromBase(fireuser);

            } else {
                userState.postValue(ERROR_SIGNIN);
                //userState.postValue(NOT_SIGNED);
            }
        }).addOnFailureListener(activity, task -> {
            userState.postValue(ERROR_SIGNIN);
            //userState.postValue(NOT_SIGNED);
        });
    }


    public LiveData<String> getUserState() {
        return userState;
    }

    public FirebaseUser getFireUser() {
        return mAuth.getCurrentUser();
    }

    public User getUser() {
        return user;
    }

}
