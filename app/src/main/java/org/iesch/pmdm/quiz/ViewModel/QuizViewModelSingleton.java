package org.iesch.pmdm.quiz.ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import org.iesch.pmdm.quiz.QuizActivity;

public class QuizViewModelSingleton extends ViewModel{

    private static QuizViewModel viewModel;

    private static Context context;
    private static ViewModelStoreOwner viewModelStoreOwner;

    public QuizViewModelSingleton(QuizActivity activity, ViewModelStoreOwner viewModelStoreOwner) {
        this.context = activity.getApplicationContext();
        this.viewModelStoreOwner = viewModelStoreOwner;
    }

    @NonNull
    public static QuizViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new ViewModelProvider(viewModelStoreOwner,
                            new ViewModelFactory(context)).get(QuizViewModel.class);
        }

        return viewModel;
    }
}
