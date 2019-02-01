package codehealthy.com.stoicly.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

public interface AuthorRepository {
    LiveData<List<Author>> getAllAuthors();

    void insertAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Author author);

    void getAuthorByName(String authorName);
    void getAuthor(int AuthorId);

    LiveData<List<QuoteAuthorJoin>> getAllQuotesByAuthor(int authorId);

    LiveData<Author> getAuthorById(int authorId);

}

