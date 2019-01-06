

package codehealthy.com.stoicly.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import codehealthy.com.stoicly.data.model.Author;

@Dao
public interface AuthorDao {

    @Query("Select * from Author where id = :authorId LIMIT 1")
    Author getAuthorById(int authorId);

    @Query("Select * from Author where name LIKE :authorName LIMIT 1")
    Author getAuthorByName(String authorName);


    @Query("Select * from Author")
    LiveData<List<Author>> getAllAuthors();

    @Insert
    void insertAuthor(Author... author);

    @Update
    void updateAuthor(Author... author);

    @Delete
    void deleteAuthor(Author... author);


}
