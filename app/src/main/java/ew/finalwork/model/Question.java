package ew.finalwork.model;

import java.util.ArrayList;
import java.util.Arrays;

class Question {

    String questionText;
    ArrayList<String> answers;
    int rightQuestion;

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

}
