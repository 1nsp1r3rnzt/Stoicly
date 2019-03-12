package com.codehealthy.stoicly.ui.author.quotelist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.codehealthy.stoicly.data.AuthorRepository;
import com.codehealthy.stoicly.data.AuthorRepositoryImpl;
import com.codehealthy.stoicly.data.QuoteRepository;
import com.codehealthy.stoicly.data.QuoteRepositoryImpl;
import com.codehealthy.stoicly.data.model.Author;
import com.codehealthy.stoicly.data.model.Quote;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;

import java.util.List;

public class AuthorQuoteListViewModel extends AndroidViewModel {

    private final LiveData<List<QuoteAuthorJoin>> allFavouriteQuotes;
    private final LiveData<List<QuoteAuthorJoin>> allQuoteByAuthorList;
    private       LiveData<Author>                author;
    private       QuoteRepository                 quoteRepository;

    AuthorQuoteListViewModel(@NonNull Application application, int authorId) {
        super(application);
        AuthorRepository authorRepository = new AuthorRepositoryImpl(getApplication(), authorId);
        quoteRepository = new QuoteRepositoryImpl(application);
        this.author = authorRepository.getAuthorById(authorId);
        this.allFavouriteQuotes = authorRepository.getFavouriteQuotesOfAuthor(authorId);
        allQuoteByAuthorList = authorRepository.getAllQuotesByAuthor(authorId);
    }

    public LiveData<List<QuoteAuthorJoin>> getAllQuoteByAuthorList() {
        return allQuoteByAuthorList;
    }

    public LiveData<Author> getAuthor() {
        return author;
    }

    void updateQuote(Quote quote) {
        quoteRepository.updateQuote(quote);
    }

    public LiveData<List<QuoteAuthorJoin>> getAllFavouriteQuotes() {
        return allFavouriteQuotes;
    }


}
