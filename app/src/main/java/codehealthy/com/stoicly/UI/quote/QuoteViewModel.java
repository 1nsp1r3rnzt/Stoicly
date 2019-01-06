package codehealthy.com.stoicly.UI.quote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codehealthy.com.stoicly.data.QuoteRepository;
import codehealthy.com.stoicly.data.QuoteRepositoryImpl;
import codehealthy.com.stoicly.data.model.Quote;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public class QuoteViewModel extends AndroidViewModel {
    private QuoteRepository                 repository;
    private LiveData<List<QuoteAuthorJoin>> allQuotesWithAuthoName;

    public QuoteViewModel(@NonNull Application application) {
        super(application);
        repository = new QuoteRepositoryImpl(application);
        allQuotesWithAuthoName = repository.getAllQuotesWithAuthorName();
    }

    public void insertQuote(Quote quote) {
        repository.insertQuote(quote);
    }

    public void updateQuote(Quote quote) {
        repository.updateQuote(quote);

    }

    public void deleteQuote(Quote quote) {
        repository.deleteQuote(quote);

    }

    LiveData<List<QuoteAuthorJoin>> getAllQuotesWithAuthorName() {
        return allQuotesWithAuthoName;
    }
}
