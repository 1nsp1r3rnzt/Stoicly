package codehealthy.com.stoicly.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import codehealthy.com.stoicly.data.model.Quote;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

@Dao
public interface QuoteDao {

    @Insert
    void insertQuote(Quote... quotes);

    @Update
    void updateQuote(Quote quote);


    @Query("SELECT quote.*,author.name as author_name, author.thumbnailUrl as thumbnail_url  from quote quote LEFT JOIN author ON author.id=quote.author_id ")
    LiveData<List<QuoteAuthorJoin>> getAllQuotesWithAuthorName();

    @Query("SELECT quote.*,author.name as author_name , author.thumbnailUrl as thumbnail_url  from quote quote LEFT JOIN author ON author.id=quote.author_id where is_favourite = 1 ")
    LiveData<List<QuoteAuthorJoin>> getFavouriteQuotes();

    @Query("Select * from quote quote WHERE author_id = :authorId")
    LiveData<List<Quote>> getQuotesByAuthor(int authorId);

    @Query("Select * from quote where id BETWEEN :startId AND (:startId+:maxRange)")
    List<Quote> getQuotesByRange(int startId, int maxRange);

    @Query("SELECT * from quote ORDER BY RANDOM() LIMIT 1")
    Quote getRandomQuote();

    @Delete
    void deleteQuote(Quote... quote);

    @Query("SELECT * from quote where category_id = :categoryId")
    LiveData<List<Quote>> getQuotesByCategory(int categoryId);

    @Query("Select COUNT() FROM quote")
    int totalQuotes();
}
