package codehealthy.com.stoicly.data;

import android.arch.lifecycle.LiveData;

import codehealthy.com.stoicly.data.model.User;

public interface UserRepository {

    LiveData<User> getUser();

    void updateUser(User user);

    void deleteUser(User user);
}
