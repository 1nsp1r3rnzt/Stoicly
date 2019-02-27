package codehealthy.com.stoicly.ui.favourite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codehealthy.com.stoicly.data.QuoteRepository;
import codehealthy.com.stoicly.data.QuoteRepositoryImpl;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

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
