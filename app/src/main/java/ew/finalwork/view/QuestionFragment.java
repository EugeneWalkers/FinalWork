package ew.finalwork.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import ew.finalwork.R;
import ew.finalwork.model.Question;
import ew.finalwork.utilities.QuestionFragmentAdapter;

public class QuestionFragment extends Fragment {

    int numberOfQuestion;
    ArrayList<RadioButton> buttons = new ArrayList<>();
    TextView question;
    Question myQuestion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            numberOfQuestion = bundle.getInt(QuestionFragmentAdapter.NUMBER);
            myQuestion = bundle.getParcelable(QuestionFragmentAdapter.QUESTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        question = v.findViewById(R.id.question);
        if (buttons.size() == 0) {
            buttons.add(v.findViewById(R.id.answer0));
            buttons.add(v.findViewById(R.id.answer1));
            buttons.add(v.findViewById(R.id.answer2));
            buttons.add(v.findViewById(R.id.answer3));
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(myQuestion.getAnswers().get(i));
        }
        question.setText(myQuestion.getQuestionText());
        return v;
    }
}
