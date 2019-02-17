package codehealthy.com.stoicly.ui.author.all;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codehealthy.com.stoicly.data.AuthorRepository;
import codehealthy.com.stoicly.data.AuthorRepositoryImpl;
import codehealthy.com.stoicly.data.model.Author;

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