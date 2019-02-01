package codehealthy.com.stoicly.UI.author;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codehealthy.com.stoicly.data.AuthorRepository;
import codehealthy.com.stoicly.data.AuthorRepositoryImpl;
import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

//what do we need to do
//
//
//
//
//
public class AuthorViewModel extends AndroidViewModel {
    private AuthorRepository                authorRepository;
    private LiveData<List<QuoteAuthorJoin>> allQuoteByAuthorList;
    private LiveData<Author>                author;
    private int                             authorId;
    private int                             numberOfAuthorQuotes;

    public AuthorViewModel(@NonNull Application application, int authorId) {
        super(application);
        authorRepository = new AuthorRepositoryImpl(application, authorId);
        this.authorId = authorId;
        this.author = authorRepository.getAuthorById(authorId);
        this.allQuoteByAuthorList = authorRepository.getAllQuotesByAuthor(authorId);
    }

    public LiveData<Author> getAuthorById(int authorId) {
        return author;
    }

    public LiveData<List<QuoteAuthorJoin>> getAllQuoteByAuthorList() {
        return allQuoteByAuthorList;
    }

    public int getCountOfAuthorQuotes() {
        return numberOfAuthorQuotes;
    }

    public void setAllQuoteByAuthorList(LiveData<List<QuoteAuthorJoin>> allQuoteByAuthorList) {
        this.allQuoteByAuthorList = allQuoteByAuthorList;
    }
}
