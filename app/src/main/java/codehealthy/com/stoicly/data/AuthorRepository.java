package codehealthy.com.stoicly.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;

public interface AuthorRepository {
    LiveData<List<Author>> getAllAuthors();

    void insertAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(Author author);

    void getAuthorByName(String authorName);

    void getAuthorById(int authorId);
}

