package org.iesch.pmdm.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;

import org.iesch.pmdm.quiz.ViewModel.QuizViewModel;
import org.iesch.pmdm.quiz.databinding.ActivityMainBinding;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private static boolean firstTime = true;

    private ActivityMainBinding binding;
    private QuizViewModel quizViewModel;

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

        if (firstTime) {
                quizViewModel.newQuestion();
                firstTime = false;
        }

        setObservers();
        setButtons();
    }

    private void setObservers () {
        quizViewModel.getQuestion().observe(this, question -> {
            binding.operationTextView.setText(question);
        });
        quizViewModel.getTrueAnswer().observe(this, trueAnswer -> {
            binding.radioButton1.setText(trueAnswer);
        });
        quizViewModel.getFalseAnswer().observe(this, falseAnswer -> {
            binding.radioButton2.setText(falseAnswer);
        });
    }

    private void setButtons() {
        binding.sendButton.setOnClickListener(v -> {
            if (buttonSelected(binding.radioButton1) && checkAnswer() ||
                buttonSelected(binding.radioButton2) && checkAnswer()) {
                viewScore(true);
            } else {
                viewScore(false);
            }
            quizViewModel.newQuestion();
        });
    }

    private void viewScore (boolean result) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
    }

    private boolean buttonSelected (RadioButton radioButton) {
        return radioButton.isActivated();
    }

    private boolean checkAnswer () {
        return quizViewModel.checkAnswer(binding.radioButton1.getText().toString());
    }
}