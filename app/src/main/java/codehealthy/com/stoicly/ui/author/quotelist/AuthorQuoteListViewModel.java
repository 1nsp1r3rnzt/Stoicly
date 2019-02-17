package codehealthy.com.stoicly.ui.author.quotelist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codehealthy.com.stoicly.data.AuthorRepository;
import codehealthy.com.stoicly.data.AuthorRepositoryImpl;
import codehealthy.com.stoicly.data.QuoteRepository;
import codehealthy.com.stoicly.data.QuoteRepositoryImpl;
import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.Quote;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public class AuthorQuoteListViewModel extends AndroidViewModel {
    private LiveData<List<QuoteAuthorJoin>> allQuoteByAuthorList;
    private LiveData<Author>                author;
    private QuoteRepository                 quoteRepository;

    AuthorQuoteListViewModel(@NonNull Application application, int authorId) {
        super(application);
        AuthorRepository authorRepository = new AuthorRepositoryImpl(getApplication(), authorId);
        quoteRepository = new QuoteRepositoryImpl(application);
        this.author = authorRepository.getAuthorById(authorId);
        this.allQuoteByAuthorList = authorRepository.getAllQuotesByAuthor(authorId);
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


}
