package ew.finalwork.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Test implements Parcelable {

    private ArrayList<Question> questions;
    private String testName;
    private String description;

    public Test(ArrayList<Question> questions, String testName){
        this.questions = questions;
        this.testName = testName;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public void addQuestion(Question question, int position){
        questions.add(position, question);
    }

    public void removeQuestion(int position){
        questions.remove(position);
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
       parcel.writeList(questions);
       parcel.writeString(testName);
       parcel.writeString(description);
    }

    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>() {
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    private Test(Parcel in) {
        questions = in.readArrayList(Question.class.getClassLoader());
        testName = in.readString();
        description = in.readString();
    }

    @Override
    public String toString(){
        StringBuilder resultBuilder = new StringBuilder("");
        resultBuilder.append(testName).append("\n").append(description);
        for (int i=0; i<questions.size(); i++){
            resultBuilder.append("\n").append(questions.get(i).toString());
        }
        return resultBuilder.toString();
    }
}
