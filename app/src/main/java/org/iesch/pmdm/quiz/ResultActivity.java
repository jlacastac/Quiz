package org.iesch.pmdm.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.iesch.pmdm.quiz.ViewModel.QuizViewModel;
import org.iesch.pmdm.quiz.ViewModel.QuizViewModelSingleton;
import org.iesch.pmdm.quiz.databinding.ActivityResultBinding;

/**
 * Clase que representa la activity que te devuelve
 * el resultado de la pregunta contestada.
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
     * Inicializa las variables necesarias y prepara
     * los componentes.
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
     * Pone el texto que corresponde a los botones
     * y TextViews.
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
     * Estabelece los listeners de los botones.
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

    private QuizViewModel getQuizViewModel () {
        return QuizViewModelSingleton.getInstance();
    }
}