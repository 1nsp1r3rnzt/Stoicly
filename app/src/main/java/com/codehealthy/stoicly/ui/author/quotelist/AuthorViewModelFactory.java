package com.codehealthy.stoicly.ui.author.quotelist;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AuthorViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int         param;

    AuthorViewModelFactory(Application application, int param) {
        this.application = application;
        this.param = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthorQuoteListViewModel.class)) {
            return (T) new AuthorQuoteListViewModel(application, param);
        }
        throw new IllegalArgumentException("Unknown Model Class");
    }
}
