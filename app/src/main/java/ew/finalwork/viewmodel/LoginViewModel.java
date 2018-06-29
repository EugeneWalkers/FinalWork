package ew.finalwork.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ew.finalwork.model.User;

public class LoginViewModel extends ViewModel {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private MutableLiveData<User> user = new MutableLiveData<>();

    public LiveData<Boolean> getIsDialogShow() {
        return isDialogShow;
    }

    public void setIsDialogShow(MutableLiveData<Boolean> isDialogShow) {
        this.isDialogShow = isDialogShow;
    }

    private MutableLiveData<Boolean> isDialogShow = new MutableLiveData<>();
    String login, password;

    public LoginViewModel() {
        super();
        user.postValue(null);
        isDialogShow.postValue(false);

    }

    public void readUserFromBase(@NonNull FirebaseUser firebaseUser){
        db = FirebaseFirestore.getInstance();
        String email = firebaseUser.getEmail();
        DocumentReference dataReader = db.collection("users").document(email);
        dataReader.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> data = document.getData();
                    String type = data.get("type").toString();
                    Map<String, String> results = new HashMap<>();
                    if (data.containsKey("results")){
                        ArrayList<String> notParcedResults = (ArrayList)data.get("results");
                        for (int i=0; i<notParcedResults.size(); i++){
                            String[] result = notParcedResults.get(i).split(":");
                            results.put(result[0], result[1]);
                        }
                    }
                    user.postValue(new User(firebaseUser, type, results));
                }
            }
            isDialogShow.postValue(false);
        }).addOnFailureListener(task->{
            isDialogShow.postValue(false);
        });
    }

    public void onLoginClicked(String login, String password) {
        isDialogShow.postValue(true);
        this.login = login;
        this.password = password;
        mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser fireuser = mAuth.getCurrentUser();
                if (fireuser != null){
                    readUserFromBase(fireuser);
                }
                else{
                    isDialogShow.postValue(false);
                    user.postValue(null);
                }
            }
            isDialogShow.postValue(false);
        }).addOnFailureListener(task->{
            isDialogShow.postValue(false);
        });
    }


    public LiveData<User> getUser() {
        return user;
    }

}
