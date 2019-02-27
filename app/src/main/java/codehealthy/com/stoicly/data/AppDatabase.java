package codehealthy.com.stoicly.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.Quote;
import codehealthy.com.stoicly.data.model.QuoteGroup;
import codehealthy.com.stoicly.data.model.User;
import codehealthy.com.stoicly.data.source.local.AuthorDao;
import codehealthy.com.stoicly.data.source.local.AuthorJsonHelper;
import codehealthy.com.stoicly.data.source.local.QuoteDao;
import codehealthy.com.stoicly.data.source.local.QuoteGroupDao;
import codehealthy.com.stoicly.data.source.local.QuoteJsonHelper;
import codehealthy.com.stoicly.data.source.local.UserDao;

@Database(entities = {Quote.class, Author.class, User.class, QuoteGroup.class}, version = 2)
abstract class AppDatabase extends RoomDatabase {
    private static final String      APP_DATABASE               = "database.db";
    private static final String      AUTHORS_JSON_RESOURCE_NAME = "authors.json";
    private static final String      QUOTES_JSON_RESOURCE_NAME  = "quotes.json";
    private static       AppDatabase INSTANCE;

    AppDatabase() {
    }

    private static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
    private static Migration MIGRATION_4_2 = new Migration(4, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    static synchronized AppDatabase getInstance(final Context context) {
//        singleton for the instance
        if (INSTANCE == null) {

            DatabaseCopier databaseCopier = DatabaseCopier.getInstance();
            databaseCopier.copyIfNoDataBaseFound(context, APP_DATABASE);
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, APP_DATABASE).
                    addMigrations(MIGRATION_1_2, MIGRATION_4_2).build();
//                disabled room insert on create as it takes time to add 900 quotes.
//                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database.db")
//                        .addCallback(new RoomDatabase.Callback() {
//
//                                         @Override
//                                         public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                                             super.onCreate(db);
//
//
////                                             populateAuthorsDatabase(context);
////                                             populateQuotesDatabase(context);
//                                         }
//                                     }
//                        )
//                        .build();
        }
        return INSTANCE;
    }


    private static void populateQuotesDatabase(@NonNull Context context) {

        List<Quote> quotesData;
        quotesData = new QuoteJsonHelper(context).getListFrom(QUOTES_JSON_RESOURCE_NAME);
        new PopulateQuoteDatabaseAsyncTask(INSTANCE, quotesData).execute();
    }

    private static void populateAuthorsDatabase(@NonNull Context context) {
        List<Author> authorList;
        authorList = new AuthorJsonHelper(context).getListFrom(AUTHORS_JSON_RESOURCE_NAME);

        new PopulateAuthorDatabaseAsyncTask(INSTANCE, authorList).execute();
    }

    abstract QuoteDao quoteDao();

    abstract AuthorDao authorDao();

    abstract UserDao userDao();

    abstract QuoteGroupDao quoteGroupDao();

    private static class PopulateQuoteDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private QuoteDao    quoteDao;
        private List<Quote> quoteList;

        PopulateQuoteDatabaseAsyncTask(AppDatabase appDatabase, List<Quote> quoteList) {

            quoteDao = appDatabase.quoteDao();
            this.quoteList = quoteList;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (Quote quote : quoteList) {
                quoteDao.insertQuote(quote);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private static class PopulateAuthorDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private AuthorDao    authorDao;
        private List<Author> authorList;

        PopulateAuthorDatabaseAsyncTask(AppDatabase appDatabase, List<Author> quoteList) {
            authorDao = appDatabase.authorDao();
            this.authorList = quoteList;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            for (Author author : authorList) {
                authorDao.insertAuthor(author);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
}


