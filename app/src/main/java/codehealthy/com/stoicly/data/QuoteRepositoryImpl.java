package codehealthy.com.stoicly.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import codehealthy.com.stoicly.data.model.Quote;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.data.source.local.QuoteDao;

public class QuoteRepositoryImpl implements QuoteRepository {
    private final static String                          TAG = QuoteRepositoryImpl.class.getSimpleName();
    private              QuoteDao                        quoteDao;
    private              LiveData<List<QuoteAuthorJoin>> allFavouriteQuotes;
    private              LiveData<List<QuoteAuthorJoin>> allQuotes;

    public QuoteRepositoryImpl(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        quoteDao = appDatabase.quoteDao();
        allQuotes = quoteDao.getAllQuotesWithAuthorName();
        allFavouriteQuotes = quoteDao.getFavouriteQuotes();
    }

    public void insertQuote(Quote quote) {
        new InsertQuoteAsyncTask(quoteDao).execute(quote);
    }

    public void updateQuote(Quote quote) {

        new UpdateQuoteAsyncTask(quoteDao).execute(quote);
    }

    public void deleteQuote(Quote quote) {
        new DeleteQuoteAsyncTask(quoteDao).execute(quote);
    }

    @Override
    public LiveData<List<QuoteAuthorJoin>> getAllFavouriteQuotes() {
        return allFavouriteQuotes;
    }


    @Override
    public LiveData<List<QuoteAuthorJoin>> getAllQuotesWithAuthorName() {
        return allQuotes;
    }

    private static class InsertQuoteAsyncTask extends AsyncTask<Quote, Void, Void> {
        private QuoteDao quoteDao;

        private InsertQuoteAsyncTask(QuoteDao quoteDao) {
            this.quoteDao = quoteDao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {

            quoteDao.insertQuote(quotes);
            return null;
        }
    }

    private static class DeleteQuoteAsyncTask extends AsyncTask<Quote, Void, Void> {
        private QuoteDao quoteDao;

        DeleteQuoteAsyncTask(QuoteDao quoteDao) {
            this.quoteDao = quoteDao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {

            quoteDao.deleteQuote(quotes);
            return null;
        }
    }

    private static class UpdateQuoteAsyncTask extends AsyncTask<Quote, Void, Void> {
        private QuoteDao quoteDao;

        UpdateQuoteAsyncTask(QuoteDao quoteDao) {
            this.quoteDao = quoteDao;
        }

        @Override
        protected Void doInBackground(Quote... quotes) {
            quoteDao.updateQuote(quotes[0]);
            return null;
        }
    }


}

