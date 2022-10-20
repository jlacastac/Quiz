package org.iesch.pmdm.quiz.ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import org.iesch.pmdm.quiz.QuizActivity;

import java.security.Provider;

/**
 * Represent a QuizViewModel factory.
 * 
 */
public class ViewModelFactory implements ViewModelProvider.Factory{

    private Context context;

   /**
    * Build a view model factory.
    *
    */
    public ViewModelFactory(Context context) {
        this.context = context;
    }

    /**
    * Create a new ViewModel
    * 
    * @return <T extends ViewModel> T
    */
    @Override
    public  <T extends ViewModel> T create(Class<T> modelClass) {
      
      if(modelClass.equals(QuizViewModel.class)) {
         return (T) new QuizViewModel(context);
      }
      
      return null;
    }
}

