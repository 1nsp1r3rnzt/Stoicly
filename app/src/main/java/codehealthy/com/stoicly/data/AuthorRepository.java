package codehealthy.com.stoicly.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public interface AuthorRepository {
    LiveData<List<QuoteAuthorJoin>> getAllQuotesByAuthor(int authorId);
    LiveData<Author> getAuthorById(int authorId);

    LiveData<List<Author>> getAllAuthors();
}

