package codehealthy.com.stoicly.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import codehealthy.com.stoicly.data.model.Quote;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public interface QuoteRepository {
    void insertQuote(Quote quote);

    void updateQuote(Quote quote);

    void deleteQuote(Quote quote);

    LiveData<List<Quote>> getAllQuotes();

    LiveData<List<QuoteAuthorJoin>> getAllQuotesWithAuthorName();
}
