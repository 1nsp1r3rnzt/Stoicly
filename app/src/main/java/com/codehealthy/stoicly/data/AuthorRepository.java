package com.codehealthy.stoicly.data;

import android.arch.lifecycle.LiveData;

import com.codehealthy.stoicly.data.model.Author;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;

import java.util.List;

public interface AuthorRepository {
    LiveData<List<QuoteAuthorJoin>> getAllQuotesByAuthor(int authorId);

    LiveData<Author> getAuthorById(int authorId);

    LiveData<List<Author>> getAllAuthors();

    LiveData<List<QuoteAuthorJoin>> getFavouriteQuotesOfAuthor(int authorId);
}

