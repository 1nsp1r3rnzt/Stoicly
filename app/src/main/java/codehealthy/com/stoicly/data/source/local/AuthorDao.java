

package codehealthy.com.stoicly.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;
import codehealthy.com.stoicly.data.model.QuoteAuthorJoin;

@Dao
public interface AuthorDao {

    @Query("Select * from Author where id = :authorId LIMIT 1")
    LiveData<Author> getAuthorById(int authorId);

    @Query("Select * from Author")
    LiveData<List<Author>> getAllAuthors();

    @Query("SELECT  quote.*,author.name as author_name, author.thumbnailUrl as thumbnail_url from quote quote LEFT OUTER JOIN author ON author.id=quote.author_id where quote.author_id=:authorId ")
    LiveData<List<QuoteAuthorJoin>> getAllQuotesByAuthor(int authorId);

    @Insert
    void insertAuthor(Author... author);


}
