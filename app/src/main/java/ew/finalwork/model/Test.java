package ew.finalwork.model;

import java.util.ArrayList;

public class Test {

    private ArrayList<Question> questions;
    private String testName;

    Test(ArrayList<Question> questions, String testName){
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
}
