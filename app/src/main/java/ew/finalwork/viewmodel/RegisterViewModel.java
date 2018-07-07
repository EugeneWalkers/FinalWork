package ew.finalwork.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ew.finalwork.model.User;
import ew.finalwork.utilities.DataUtility;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<User> user;

    public RegisterViewModel(){
        super();
        user = new MutableLiveData<>();
    }

    public void createUser(String login, String password){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, password).addOnCompleteListener(task -> {
            //Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
            setDocument(login);
        }).addOnFailureListener(e -> {
            //Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            user.postValue(null);
        });
    }

    private void setDocument(String email){
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("type", "testedUser");
        FirebaseFirestore.getInstance().collection(DataUtility.USERS).document(email).set(userInfo);
        user.postValue(new User(FirebaseAuth.getInstance().getCurrentUser(), "testedUser", new ArrayList<>()));
    }

    public LiveData<User> getUser(){
        return user;
    }

}
