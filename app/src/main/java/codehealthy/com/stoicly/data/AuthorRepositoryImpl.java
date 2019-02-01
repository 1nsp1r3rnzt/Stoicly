package codehealthy.com.stoicly.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;
import codehealthy.com.stoicly.data.source.local.AuthorDao;

public class AuthorRepositoryImpl implements AuthorRepository {
    private AuthorDao                       authorDao;
    private int                             authorId;
    private LiveData<List<Author>>          allAuthors;
    private LiveData<List<QuoteAuthorJoin>> allQuotesByAuthor;

    public AuthorRepositoryImpl(Application application, int authorId) {
        authorDao = AppDatabase.getInstance(application).authorDao();
        allAuthors = authorDao.getAllAuthors();
        this.authorId = authorId;
        allQuotesByAuthor = authorDao.getAllQuotesByAuthor(authorId);

    }

    public LiveData<List<Author>> getAllAuthors() {
        return allAuthors;
    }

    public void insertAuthor(Author author) {
        new InsertAuthorAsyncTask(authorDao).execute(author);
    }

    public void updateAuthor(Author author) {
        new UpdateAuthorAsyncTask(authorDao).execute(author);
    }

    public void deleteAuthor(Author author) {
        new DeleteAuthorAsyncTask(authorDao).execute(author);
    }

    @Override
    public void getAuthorByName(String authorName) {

    }

    @Override
    public void getAuthor(int AuthorId) {

    }

    @Override
    public LiveData<List<QuoteAuthorJoin>> getAllQuotesByAuthor(int authorId) {
        return allQuotesByAuthor;
    }

    @Override
    public LiveData<Author> getAuthorById(int authorId) {
        return null;
    }


    private static class InsertAuthorAsyncTask extends AsyncTask<Author, Void, Void> {
        private AuthorDao authorDao;

        InsertAuthorAsyncTask(AuthorDao authorDao) {
            this.authorDao = authorDao;
        }

        @Override
        protected Void doInBackground(Author... authors) {
            authorDao.insertAuthor(authors);
            return null;
        }
    }

    private static class UpdateAuthorAsyncTask extends AsyncTask<Author, Void, Void> {

        private AuthorDao authorDao;

        UpdateAuthorAsyncTask(AuthorDao authorDao) {
            this.authorDao = authorDao;
        }

        @Override
        protected Void doInBackground(Author... authors) {
            authorDao.updateAuthor(authors);
            return null;
        }
    }

    private static class DeleteAuthorAsyncTask extends AsyncTask<Author, Void, Void> {
        private AuthorDao authorDao;

        private DeleteAuthorAsyncTask(AuthorDao authorDao) {
            this.authorDao = authorDao;
        }

        @Override
        protected Void doInBackground(Author... authors) {
            authorDao.deleteAuthor(authors);
            return null;
        }
    }

}
