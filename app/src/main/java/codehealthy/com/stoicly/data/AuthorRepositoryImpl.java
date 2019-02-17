package codehealthy.com.stoicly.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.data.source.local.AuthorDao;

public class AuthorRepositoryImpl implements AuthorRepository {
    private LiveData<Author>                author;
    private LiveData<List<QuoteAuthorJoin>> allQuotesByAuthor;
    private LiveData<List<Author>>          allAuthorList;


    public AuthorRepositoryImpl(Application application, int authorId) {
        AuthorDao authorDao = AppDatabase.getInstance(application).authorDao();
        allQuotesByAuthor = authorDao.getAllQuotesByAuthor(authorId);
        if (authorId > 0) {
            author = authorDao.getAuthorById(authorId);
        }
        allAuthorList = authorDao.getAllAuthors();
    }

    @Override
    public LiveData<List<QuoteAuthorJoin>> getAllQuotesByAuthor(int authorId) {
        return allQuotesByAuthor;
    }

    @Override
    public LiveData<Author> getAuthorById(int authorId) {
        return author;
    }

    @Override
    public LiveData<List<Author>> getAllAuthors() {
        return allAuthorList;
    }

}
