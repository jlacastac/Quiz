package org.iesch.pmdm.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.iesch.pmdm.quiz.ViewModel.QuizViewModel;
import org.iesch.pmdm.quiz.databinding.ActivityResultBinding;

import java.util.ResourceBundle;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;
    private QuizViewModel quizViewModel;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
        setButtons();
    }

    private void initialize () {
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        extras = getIntent().getExtras();

        setContentView(binding.getRoot());

        if(!extras.getBoolean("end")) {
            if (extras.getBoolean("result")) {
                binding.resultTextView.setText(R.string.win_text);
            } else {
                binding.resultTextView.setText(R.string.defeat_text);
            }
            binding.resultButton.setText(R.string.next_button);
        } else {
            binding.resultTextView.setText(R.string.end_text);
            binding.resultButton.setText(R.string.next_button_end_text);
        }
    }

    private void setButtons () {
        binding.resultButton.setOnClickListener(v -> {
            finish();
        });
    }
}