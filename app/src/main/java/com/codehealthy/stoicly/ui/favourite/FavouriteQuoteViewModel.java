package com.codehealthy.stoicly.ui.favourite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.codehealthy.stoicly.data.QuoteRepository;
import com.codehealthy.stoicly.data.QuoteRepositoryImpl;
import com.codehealthy.stoicly.data.model.QuoteAuthorJoin;

import java.util.List;

public class FavouriteQuoteViewModel extends AndroidViewModel {

    private final LiveData<List<QuoteAuthorJoin>> favouriteQuotes;
    private final QuoteRepository                 quoteRepository;

    public FavouriteQuoteViewModel(@NonNull Application application) {
        super(application);
        quoteRepository = new QuoteRepositoryImpl(application);
        this.favouriteQuotes = quoteRepository.getAllFavouriteQuotes();
    }


    LiveData<List<QuoteAuthorJoin>> getFavouriteQuotes() {
        return favouriteQuotes;
    }


    public void updateQuote(QuoteAuthorJoin quote) {
        quoteRepository.updateQuote(quote);
    }
}
