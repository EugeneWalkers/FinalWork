package ew.finalwork.utilities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ew.finalwork.model.Question;
import ew.finalwork.view.QuestionFragment;

public class QuestionFragmentAdapter extends FragmentPagerAdapter {

    public static final String QUESTION = "question";
    public static final String NUMBER = "number";

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    ArrayList<Question> questions;

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;

    public QuestionFragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment qf = new QuestionFragment();
        Bundle bundle = new Bundle();
        Question question = questions.get(position);
        bundle.putParcelable(QUESTION, question);
        bundle.putInt(NUMBER, position);
        qf.setArguments(bundle);
        return qf;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Question " + position;
    }

    @Override
    public int getCount() {
        return count;
    }
}

