package org.iesch.pmdm.quiz.ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import org.iesch.pmdm.quiz.QuizActivity;

import java.security.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory{

    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public  <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new QuizViewModel(context);
    }
}

