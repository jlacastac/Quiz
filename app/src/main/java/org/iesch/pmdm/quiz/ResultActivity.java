package org.iesch.pmdm.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.iesch.pmdm.quiz.ViewModel.QuizViewModel;
import org.iesch.pmdm.quiz.ViewModel.QuizViewModelSingleton;
import org.iesch.pmdm.quiz.databinding.ActivityResultBinding;

/**
 * Class that represents the result screen.
 *
 */
public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    /**
     * Initialize variables and set everything needed.
     *
     */
    private void initialize () {
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        extras = getIntent().getExtras();

        setContentView(binding.getRoot());

        setComponentsTexts();
        setListeners();
    }

    /**
     * Set the texts of the result and the button.
     *
     */
    private void setComponentsTexts() {
        if(!extras.getBoolean(QuizActivity.END)) {

            if (extras.getBoolean(QuizActivity.RESULT)) {
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

    /**
     * Set the button listeners
     *
     */
    private void setListeners() {
        binding.resultButton.setOnClickListener(v -> {
            if(getQuizViewModel().getQuestionNumber().getValue() == 3) {
                getQuizViewModel().resetQuestionNumber();
            }

            getQuizViewModel().newQuestion();
            finish();
        });
    }

   /**
    * Get the quiz model.
    * 
    * @return QuizViewModel
    */
    private QuizViewModel getQuizViewModel () {
        return QuizViewModelSingleton.getInstance();
    }
}
