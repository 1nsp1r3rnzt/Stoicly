package com.codehealthy.stoicly.data;

import android.arch.lifecycle.LiveData;

import com.codehealthy.stoicly.data.model.Quote;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;

import java.util.List;

public interface QuoteRepository {
    void insertQuote(Quote quote);

    void updateQuote(Quote quote);

    void deleteQuote(Quote quote);

    LiveData<List<QuoteAuthorJoin>> getAllFavouriteQuotes();

    LiveData<List<QuoteAuthorJoin>> getAllQuotesWithAuthorName();
}
