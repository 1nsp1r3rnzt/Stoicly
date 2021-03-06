package com.codehealthy.stoicly.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.codehealthy.stoicly.data.model.Author;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;
import com.codehealthy.stoicly.data.source.local.AuthorDao;

import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {
    private LiveData<Author>                author;
    private LiveData<List<QuoteAuthorJoin>> allQuotesByAuthor;
    private LiveData<List<Author>>          allAuthorList;
    private LiveData<List<QuoteAuthorJoin>> favouriteQuoteAuthorList;


    public AuthorRepositoryImpl(Application application, int authorId) {
        AuthorDao authorDao = AppDatabase.getInstance(application).authorDao();
        allQuotesByAuthor = authorDao.getAllQuotesByAuthor(authorId);
        if (authorId > 0) {
            author = authorDao.getAuthorById(authorId);
            favouriteQuoteAuthorList = authorDao.getFavouriteQuotesByAuthor(authorId);
        }
        allAuthorList = authorDao.getAllAuthors();
    }

    @Override
    public LiveData<List<QuoteAuthorJoin>> getAllQuotesByAuthor(int authorId) {
        return allQuotesByAuthor;
    }

    @Override
    public LiveData<Author> getAuthorById(int authorId) {
        return author;
    }

    @Override
    public LiveData<List<Author>> getAllAuthors() {
        return allAuthorList;
    }

    @Override
    public LiveData<List<QuoteAuthorJoin>> getFavouriteQuotesOfAuthor(int authorId) {
        return favouriteQuoteAuthorList;
    }

}
