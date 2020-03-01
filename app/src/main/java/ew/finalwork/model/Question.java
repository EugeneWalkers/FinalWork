package ew.finalwork.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class Question implements Parcelable{

    private String questionText;
    private ArrayList<String> answers;
    private int rightQuestion;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getRightQuestion() {
        return rightQuestion;
    }

    public void setRightQuestion(int rightQuestion) {
        this.rightQuestion = rightQuestion;
    }

    public Question(String questionText, ArrayList<String> answers, int rightQuestion) {
        this.questionText = questionText;
        this.answers = answers;
        this.rightQuestion = rightQuestion;
    }

    public Question(String notParcedQuestion){
        String[] question = notParcedQuestion.split(":");
        this.questionText = question[0];
        this.answers = new ArrayList<>();
        this.answers.addAll(Arrays.asList(question).subList(1, question.length - 1));
        this.rightQuestion = Integer.parseInt(question[question.length - 1]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(answers);
        parcel.writeString(questionText);
        parcel.writeInt(rightQuestion);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) {
        answers = in.readArrayList(String.class.getClassLoader());
        questionText = in.readString();
        rightQuestion = in.readInt();
    }

    @Override
    public String toString(){
        StringBuilder resultBuilder = new StringBuilder("");
        resultBuilder.append(questionText).append(":");
        for (String answer:answers){
            resultBuilder.append(answer).append(":");
        }
        resultBuilder.append(rightQuestion);
        return resultBuilder.toString();
    }
}
