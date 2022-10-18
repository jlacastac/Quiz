package org.iesch.pmdm.quiz;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.iesch.pmdm.quiz.ViewModel.QuizViewModel;
import org.iesch.pmdm.quiz.databinding.ActivityMainBinding;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private static boolean firstTime = true;

    private ActivityMainBinding binding;
    private QuizViewModel quizViewModel;


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
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quizViewModel.initialize(this);
        setComponents();

        if (firstTime) {
                quizViewModel.newQuestion();
                firstTime = false;
        }

        setObservers();
        setButtons();
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
        quizViewModel.getQuestion().observe(this, question -> {
            operationTextView.setText(question);
        });
        quizViewModel.getTrueAnswer().observe(this, trueAnswer -> {
            radioButton1.setText(trueAnswer);
        });
        quizViewModel.getFalseAnswer().observe(this, falseAnswer -> {
            radioButton2.setText(falseAnswer);
        });
        quizViewModel.getQuestionNumber().observe(this, questionNumber -> {
            switch (quizViewModel.getQuestionNumber().getValue().intValue()) {
                case 1:
                    progressTextView.setText(R.string.progress_0);
                    progressBar.setProgress(0);
                    break;

                case 2:
                    progressBar.setProgress(33);
                    progressTextView.setText(R.string.progress_1);
                    break;

                case 3:
                    progressBar.setProgress(66);
                    progressTextView.setText(R.string.progress_2);
                    break;
            }
        });
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
        if(quizViewModel.getQuestionNumber().getValue() < 3) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("end", false);
            intent.putExtra("result", result);
            startActivity(intent);
        }

        if(quizViewModel.getQuestionNumber().getValue() == 3) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("end", true);
            intent.putExtra("result", result);
            startActivity(intent);
            quizViewModel.resetQuiz();
        }
        quizViewModel.newQuestion();
    }

    private RadioButton buttonSelected () {
        return radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
    }

    private boolean checkAnswer (RadioButton radioButton) {
        return quizViewModel.checkAnswer(radioButton.getText().toString());
    }
}