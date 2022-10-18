package org.iesch.pmdm.quiz.ViewModel;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import org.iesch.pmdm.quiz.R;

import java.util.Random;


public class QuizViewModel extends ViewModel {

    private String[] questions;
    private String[] answers;

    private MutableLiveData<String> question = new MutableLiveData<>();
    private MutableLiveData<String> trueAnswer = new MutableLiveData<>();
    private MutableLiveData<String> falseAnswer = new MutableLiveData<>();

    private MutableLiveData<Integer> questionNumber = new MutableLiveData<>();

    public QuizViewModel () {
        resetQuiz();
    }

    public MutableLiveData<String> getFalseAnswer() {
        return falseAnswer;
    }

    public void newQuestion () {
        Random random = new Random();
        int quizNumber = random.nextInt(questions.length);

        System.out.println(quizNumber);

        question.setValue(questions[quizNumber]);
        trueAnswer.setValue(answers[quizNumber]);
        falseAnswer.setValue(getRandomAnswer());
        questionNumber.setValue(questionNumber.getValue() + 1);
    }

    public void initialize (Context context) {
        Resources resources = context.getResources();

        questions = resources.getStringArray(R.array.questions);
        answers = resources.getStringArray(R.array.answers);
    }

    public void resetQuiz () {
        questionNumber.setValue(0);
    }

    public boolean checkAnswer (String answer) {
        return this.trueAnswer.getValue().equals(answer);
    }

    public MutableLiveData<String> getQuestion() {
        if(question == null) {
            question = new MutableLiveData<>();
        }
        return question;
    }

    public String getRandomAnswer() {
        return  answers[new Random().nextInt(answers.length)];
    }

    public MutableLiveData<String> getTrueAnswer() {
        if(trueAnswer == null) {
            trueAnswer = new MutableLiveData<>();
        }
        return trueAnswer;
    }
}
