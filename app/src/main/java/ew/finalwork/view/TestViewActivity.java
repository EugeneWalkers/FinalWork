package ew.finalwork.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ew.finalwork.R;
import ew.finalwork.model.Question;
import ew.finalwork.model.Test;
import ew.finalwork.model.TestResult;
import ew.finalwork.utilities.QuestionFragmentAdapter;
import ew.finalwork.viewmodel.TestViewViewModel;

public class TestViewActivity extends AppCompatActivity {

    public static final String RESULT = "result";

    private TestViewViewModel viewModel;

    QuestionFragmentAdapter questionFragmentAdapter;

    private ViewPager pager;
    private PagerTabStrip strip;
    private double resultOfTest = 0;
    private ArrayList<Question> questions;
    private String nameTest;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        viewModel = ViewModelProviders.of(this).get(TestViewViewModel.class);
        test = getIntent().getParcelableExtra(TestsFragment.TEST);
        Toolbar toolbar = findViewById(R.id.toolbar_test);
        setPager();
        toolbar.setTitle(nameTest);
        Button finisher = findViewById(R.id.finisher);
        finisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishTest();
            }
        });

    }

    private void setPager() {
        questions = test.getQuestions();
        nameTest = test.getTestName();

        pager = findViewById(R.id.pager);
        strip = pager.findViewById(R.id.pagerTitleStrip);

        if (questions.size() > 0) {
            questionFragmentAdapter = new QuestionFragmentAdapter(getSupportFragmentManager());
            questionFragmentAdapter.setQuestions(questions);
            questionFragmentAdapter.setCount(questions.size());
            pager.setAdapter(questionFragmentAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishTest();
    }

    private void finishTest() {
        QuestionFragment fragment;
        for (int i = 0; i < questions.size(); i++) {
            fragment = (QuestionFragment) questionFragmentAdapter.instantiateItem(pager, i);
            if (fragment.buttons.size() != 0) {

                int right = questions.get(i).getRightQuestion();
                if (fragment.buttons.get(right).isChecked()) {
                    resultOfTest++;
                }
            }

        }

        resultOfTest /= (double) questions.size();
        Intent result = new Intent();
        result.putExtra(RESULT, new TestResult(nameTest, String.valueOf(resultOfTest)));
        setResult(MainActivity.REQUEST_OK, result);
        finish();
    }

}
