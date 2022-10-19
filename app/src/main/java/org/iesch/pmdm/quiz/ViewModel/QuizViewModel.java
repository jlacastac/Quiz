package org.iesch.pmdm.quiz.ViewModel;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import org.iesch.pmdm.quiz.R;

import java.util.Random;

/**
 * Class that represents a quiz.
 *
 */
public class QuizViewModel extends ViewModel {

    private String[] questions;
    private String[] answers;

    private MutableLiveData<String> question = new MutableLiveData<>();
    private MutableLiveData<String> trueAnswer = new MutableLiveData<>();
    private MutableLiveData<String> falseAnswer = new MutableLiveData<>();
    private MutableLiveData<Integer> questionNumber = new MutableLiveData<>();

    /**
     * Builds a QuizViewModel.
     *
     */
    public QuizViewModel (Context context) {
        initialize(context);
    }

    /**
     * Get a new question for the quiz.
     *
     */
    public void newQuestion () {
        Random random = new Random();
        int quizNumber = random.nextInt(questions.length);

        System.out.println(quizNumber);

        question.setValue(questions[quizNumber]);
        trueAnswer.setValue(answers[quizNumber]);
        falseAnswer.setValue(getRandomAnswer());
        questionNumber.setValue(questionNumber.getValue() + 1);
    }

    /**
     * Initialize the variables for the quiz.
     *
     * @param context
     */
    public void initialize (Context context) {
        Resources resources = context.getResources();
        questions = resources.getStringArray(R.array.questions);
        answers = resources.getStringArray(R.array.answers);

        resetQuestionNumber();
    }

    /**
     * Set the question number to 0.
     *
     */
    public void resetQuestionNumber() {
        questionNumber.setValue(0);
    }

    /**
     * Check if the answer is true.
     * @param answer
     * @return boolean
     */
    public boolean checkAnswer (String answer) {
        return this.trueAnswer.getValue().equals(answer);
    }

    /**
     *  Get a random answer from all the answers.
     *
     * @return String
     */
    public String getRandomAnswer() {
        return  answers[new Random().nextInt(answers.length)];
    }

    /**
     * Get the current quiz question.
     *
     * @return MutableLiveData<String>
     */
    public MutableLiveData<String> getQuestion() {
        return question;
    }

    /**
     * Get the current quiz question.
     *
     * @return MutableLiveData<String>
     */
    public MutableLiveData<String> getFalseAnswer() {
        return falseAnswer;
    }

    /**
     * Get the question number.
     *
     * @return MutableLiveData<Integer>
     */
    public MutableLiveData<Integer> getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Get the true answer of the question.
     *
     * @return MutableLiveData<String>
     */
    public MutableLiveData<String> getTrueAnswer() {
        return trueAnswer;
    }
}
