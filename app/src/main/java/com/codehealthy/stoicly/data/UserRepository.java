package com.codehealthy.stoicly.data;

import android.arch.lifecycle.LiveData;

import com.codehealthy.stoicly.data.model.User;

public interface UserRepository {

    LiveData<User> getUser();

    void updateUser(User user);

    void deleteUser(User user);
}
