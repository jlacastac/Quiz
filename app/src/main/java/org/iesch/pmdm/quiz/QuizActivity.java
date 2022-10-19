package org.iesch.pmdm.quiz;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.iesch.pmdm.quiz.ViewModel.QuizViewModel;
import org.iesch.pmdm.quiz.ViewModel.QuizViewModelSingleton;
import org.iesch.pmdm.quiz.databinding.ActivityMainBinding;

public class QuizActivity extends AppCompatActivity {

    public static final String END = "end";
    public static final String RESULT = "result";

    public static final int PROGRESS_0  = 33;
    public static final int PROGRESS_1  = 66;
    public static final int PROGRESS_2  = 100;

    public static final int QUESTION_0  = 1;
    public static final int QUESTION_1  = 2;
    public static final int QUESTION_2  = 3;

    private static boolean firstTime = true;

    private ActivityMainBinding binding;

    private ProgressBar progressBar;
    private Button sendButton;
    private TextView operationTextView;
    private TextView questionTextView;
    private TextView progressTextView;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
    }

    private void initialize () {
        new QuizViewModelSingleton(this, this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setComponents();
        setObservers();
        setButtons();

        playFirstTime();
    }

    private void playFirstTime () {
        if (firstTime) {
            QuizViewModelSingleton.getInstance().newQuestion();
            firstTime = false;
        }
    }

    private void setComponents () {
        progressBar = binding.progressBar;
        progressTextView = binding.progressTextView;
        operationTextView = binding.operationTextView;
        questionTextView = binding.questionTextView;
        sendButton = binding.sendButton;
        radioGroup = binding.radioGroup;
        radioButton1 = binding.radioButton1;
        radioButton2 = binding.radioButton2;

        questionTextView.setText(R.string.give_answer_text);
        progressTextView.setText(R.string.progress_0);
    }

    private void setObservers () {
        setObserver(getQuestion(), operationTextView);
        setObserver(getTrueAnswer(), radioButton1);
        setObserver(getFalseAnswer(), radioButton2);
        setObserver(getQuestionNumber(), null);


//        quizViewModel.getQuestion().observe(this, question -> {
//            operationTextView.setText(question);
//        });
//        quizViewModel.getTrueAnswer().observe(this, trueAnswer -> {
//            radioButton1.setText(trueAnswer);
//        });
//        quizViewModel.getFalseAnswer().observe(this, falseAnswer -> {
//            radioButton2.setText(falseAnswer);
//        });
//        quizViewModel.getQuestionNumber().observe(this, questionNumber -> {
//            setProgress();
//        });
    }

    private void setObserver (MutableLiveData data, TextView component) {
        data.observe(this, item -> {

            if(component == null) {
                setProgress();
            } else {
                component.setText(item.toString());
            }

        });
    }


    private void setText (TextView component, String text) {
        component.setText(text);
    }


    private void setButtons() {
        sendButton.setOnClickListener(v -> {
            if (buttonSelected().equals(radioButton1) && checkAnswer(radioButton1) ||
                    buttonSelected().equals(radioButton2) && checkAnswer(radioButton2)) {
                viewScore(true);
            } else {
                viewScore(false);
            }
        });
    }

    private void viewScore (boolean result) {
        changeActivity(getQuestionNumber().getValue(), result);
    }

    private void changeActivity (int questionNumber, boolean result) {

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("result", result);

        if(getQuestionNumber().getValue() == 3) {
            intent.putExtra("end", true);
        } else {
            intent.putExtra("end", false);
        }

        startActivity(intent);
    }

    private void setProgress () {
        switch (getQuestionNumber().getValue()) {
            case QUESTION_0:
                setText(progressTextView, getString(R.string.progress_0));
                progressBar.setProgress(PROGRESS_0);
                break;

            case QUESTION_1:
                progressBar.setProgress(PROGRESS_1);
                progressTextView.setText(R.string.progress_1);
                break;

            case QUESTION_2:
                progressBar.setProgress(PROGRESS_2);
                progressTextView.setText(R.string.progress_2);
                break;
        }
    }


    private RadioButton buttonSelected () {
        return radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
    }

    private boolean checkAnswer (RadioButton radioButton) {
        return getQuizViewModel().checkAnswer(radioButton.getText().toString());
    }

    private MutableLiveData<String> getQuestion () {
        return getQuizViewModel().getQuestion();
    }

    private MutableLiveData<String> getTrueAnswer () {
        return getQuizViewModel().getTrueAnswer();
    }

    private MutableLiveData<String> getFalseAnswer () {
        return getQuizViewModel().getFalseAnswer();
    }

    private MutableLiveData<Integer> getQuestionNumber () {
        return getQuizViewModel().getQuestionNumber();
    }

    private QuizViewModel getQuizViewModel () {
        return QuizViewModelSingleton.getInstance();
    }
}