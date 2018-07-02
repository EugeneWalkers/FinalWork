package ew.finalwork.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class User implements Parcelable{

    private FirebaseUser user;
    private String type;
    private Map<String, String> results;

    public User(FirebaseUser user, String type, Map<String, String> results) {
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

    public Map<String, String> getResults() {
        return results;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(user, i);
        parcel.writeString(type);
        parcel.writeMap(results);
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
        in.readMap(results, Map.class.getClassLoader());

    }
}
