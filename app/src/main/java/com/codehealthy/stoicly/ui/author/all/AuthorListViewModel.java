package com.codehealthy.stoicly.ui.author.all;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.codehealthy.stoicly.data.AuthorRepository;
import com.codehealthy.stoicly.data.AuthorRepositoryImpl;
import com.codehealthy.stoicly.data.model.Author;

import java.util.List;

public class AuthorListViewModel extends AndroidViewModel {

    private final LiveData<List<Author>> allAuthors;

    public AuthorListViewModel(@NonNull Application application) {
        super(application);
        AuthorRepository authorRepository = new AuthorRepositoryImpl(getApplication(), 0);
        allAuthors = authorRepository.getAllAuthors();
    }

    LiveData<List<Author>> getAllAuthors() {
        return allAuthors;
    }
}