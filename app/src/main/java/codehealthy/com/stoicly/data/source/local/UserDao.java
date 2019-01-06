package codehealthy.com.stoicly.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import codehealthy.com.stoicly.data.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * from user LIMIT 1")
    LiveData<User> getUser();

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

}
