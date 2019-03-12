package com.codehealthy.stoicly.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.codehealthy.stoicly.data.model.User;
import com.codehealthy.stoicly.data.source.local.UserDao;

public class
UserRepositoryImpl implements UserRepository {
    private UserDao        userDao;
    private LiveData<User> user;

    public UserRepositoryImpl(Application applicationContext) {
        userDao = AppDatabase.getInstance(applicationContext).userDao();
        user = userDao.getUser();
    }

    @Override
    public LiveData<User> getUser() {
        return user;

    }

    @Override
    public void updateUser(User user) {
        new UpdateUserAsyncTask(userDao).execute();
    }

    @Override
    public void deleteUser(User user) {
        new DeleteUserAsyncTask(userDao).execute();
    }


    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        //we need a connection to the database.
//        we need to use database access object.
        DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }


    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }
}
