package com.codehealthy.stoicly.ui.allquote;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class QuoteViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public QuoteViewModelFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(com.codehealthy.stoicly.ui.allquote.QuoteViewModel.class)) {
            return (T) new com.codehealthy.stoicly.ui.allquote.QuoteViewModel(application);
        }
        throw new IllegalArgumentException("Unknown Model Class");
    }


}
