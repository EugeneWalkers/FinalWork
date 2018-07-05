package ew.finalwork.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User implements Parcelable{

    private FirebaseUser user;
    private String type;
    private ArrayList<TestResult> results;

    public User(FirebaseUser user, String type, ArrayList<TestResult> results) {
        this.user = user;
        this.type = type;
        this.results = results;

    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<TestResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<TestResult> results) {
        this.results = results;
    }

    public void addResult(TestResult result){
        boolean isExist = false;
        for (int i=0; i<results.size(); i++){
            if (results.get(i).getTestName().equals(result.getTestName())){
                isExist = true;
                results.get(i).setTestResult(result.getTestResult());
            }
        }
        if (!isExist){
            this.results.add(result);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(user, i);
        parcel.writeString(type);
        parcel.writeList(results);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        user = in.readParcelable(FirebaseUser.class.getClassLoader());
        type = in.readString();
        results = in.readArrayList(TestResult.class.getClassLoader());

    }

    public ArrayList<String> getStringResults(){
        ArrayList<String> results = new ArrayList<>();
        for (int i=0; i<this.results.size(); i++){
            results.add(this.results.get(i).toString());
        }
        return results;
    }
}
