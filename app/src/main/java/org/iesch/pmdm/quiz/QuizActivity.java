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

/**
 * Class that represents the quiz.
 * 
 */
public class QuizActivity extends AppCompatActivity {

    /**
     * Constants for the state of the game.
     * 
     */
    public static final String END = "end";
    public static final String RESULT = "result";

    public static final int PROGRESS_0  = 33;
    public static final int PROGRESS_1  = 66;
    public static final int PROGRESS_2  = 100;

    public static final int QUESTION_0  = 1;
    public static final int QUESTION_1  = 2;
    public static final int QUESTION_2  = 3;
    /**
    * Variable used to know if its the first quiz
    * since the app opened.
    * 
    */
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
    /**
    * Initialize the app creating a quiz and setting
    * the components, observers and listeners
    * 
    */
    private void initialize () {
        new QuizViewModelSingleton(this, this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setComponents();
        setObservers();
        setListeners();

        playFirstTime();
    }

    /**
     * Checks if its the first quiz before opening 
     * the application, if its true it create a new 
     * question.
     * 
     */
    private void playFirstTime () {
        if (firstTime) {
            QuizViewModelSingleton.getInstance().newQuestion();
            firstTime = false;
        }
    }

   /**
    * Obtain the components and set the text for them
    * 
    */ 
    private void setComponents () {
        progressBar = binding.progressBar;
        progressTextView = binding.progressTextView;
        operationTextView = binding.operationTextView;
        questionTextView = binding.questionTextView;
        sendButton = binding.sendButton;
        radioGroup = binding.radioGroup;
        radioButton1 = binding.radioButton1;
        radioButton2 = binding.radioButton2;

        setText(questionTextView, R.string.give_answer_text);
        setText(progressTextView, R.string.progress_0);
    }

   /**
    * Set the observers to the data and actions.
    * 
    */ 
    private void setObservers () {
        setObserver(getQuestion(), operationTextView);
        setObserver(getTrueAnswer(), radioButton1);
        setObserver(getFalseAnswer(), radioButton2);
        setObserver(getQuestionNumber(), null);
    }

   /**
    * Set one observer to the data and action.
    * 
    * @param MutableLiveData
    * @param TextView
    */ 
    private void setObserver (MutableLiveData data, TextView component) {
        data.observe(this, item -> {

            if(component == null) {
                setProgress();
            } else {
                component.setText(item.toString());
            }
            
        });
    }

   /**
    * Set a component text.
    * 
    * @param TextView
    * @param String
    */ 
    private void setText (TextView component, String text) {
        component.setText(text);
    }

   /**
    * Set the button listeners.
    * 
    */ 
    private void setListeners() {
        sendButton.setOnClickListener(v -> {
            if (getSelectedButton().equals(radioButton1) && checkAnswer(radioButton1) ||
                    getSelectedButton().equals(radioButton2) && checkAnswer(radioButton2)) {
                viewResult(true);
            } else {
                viewResult(false);
            }
        });
    }

   /**
    * See the result of the question.
    * 
    * @param boolean
    */ 
    private void viewResult (boolean result) {
        changeActivity(getQuestionNumberValue(), result);
    }

   /**
    * Change actual QuizActivity activity 
    * to ResultActivity.
    * 
    * @param int
    * @param boolean
    */ 
    private void changeActivity (int questionNumber, boolean result) {

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(RESULT, result);

        if(getQuestionNumberValue() == QUESTION_2) {
            intent.putExtra(END, true);
        } else {
            intent.putExtra(END, false);
        }

        startActivity(intent);
    }

    /**
    * Set the progress bar and text view with
    * the actual quiz progress.
    * 
    */ 
    private void setProgress () {
        switch (getQuestionNumberValue() {
            case QUESTION_0:
                setText(progressTextView, getString(R.string.progress_0));
                progressBar.setProgress(PROGRESS_0);
                break;

            case QUESTION_1:
               setText(progressTextView, getString(R.string.progress_1));
                progressBar.setProgress(PROGRESS_1);
                break;

            case QUESTION_2:
                setText(progressTextView, getString(R.string.progress_2));
                progressBar.setProgress(PROGRESS_2);
                break;
        }
    }

    /**
    * Get the selected radio button.
    * 
    * @return RadioButton
    */ 
    private RadioButton getSelectedButton () {
        return radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
    }

   /**
    * Checks if the user answer is true.
    * 
    * @param RadioButton
    * @return boolean
    */ 
    private boolean checkAnswer (RadioButton radioButton) {
        return getQuizViewModel().checkAnswer(radioButton.getText().toString());
    }

   /**
    * Get the MutableLiveData of the question.
    * 
    * @return MutableLiveData<String>
    */ 
    private MutableLiveData<String> getQuestion () {
        return getQuizViewModel().getQuestion();
    }

   /**
    * Get the MutableLiveData of the true answer.
    * 
    * @return MutableLiveData<String>
    */
    private MutableLiveData<String> getTrueAnswer () {
        return getQuizViewModel().getTrueAnswer();
    }

   /**
    * Get the MutableLiveData of the false answer.
    * 
    * @return MutableLiveData<String>
    */
    private MutableLiveData<String> getFalseAnswer () {
        return getQuizViewModel().getFalseAnswer();
    }

   /**
    * Get the MutableLiveData of the question number.
    * 
    * @return MutableLiveData<Integer>
    */
    private MutableLiveData<Integer> getQuestionNumber () {
        return getQuizViewModel().getQuestionNumber();
    }
    
   /**
    * Get the quiz question value as string.
    * 
    * @return String
    */
    private String getQuestionValue () {
        return getQuizViewModel().getQuestion().getValue();
    }
    
   /**
    * Get the quiz true answer value as string.
    * 
    * @return String
    */
    private String getTrueAnswerValue () {
        return getQuizViewModel().getTrueAnswer().getValue();
    }

   /**
    * Get the quiz false answer value as string.
    * 
    * @return String
    */
    private String getFalseAnswerValue () {
        return getQuizViewModel().getFalseAnswer().getValue();
    }

   /**
    * Get the quiz question number value as integer.
    * 
    * @return Integer
    */
    private Integer getQuestionNumberValue () {
        return getQuizViewModel().getQuestionNumber().getValue();
    }

   /**
    * Get the quiz.
    * 
    * return QuizViewModel
    */
    private QuizViewModel getQuizViewModel () {
        return QuizViewModelSingleton.getInstance();
    }
}
