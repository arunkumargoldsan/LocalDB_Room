package com.example.scriflare_iq2.room_db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private Dao dao;
    private LiveData<List<UserModal>> allUsers;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        dao = database.Dao();
        allUsers = dao.getAllUser();
    }

    public void insert(UserModal modal) {
        new InsertUserAsyncTask(dao).execute(modal);
    }

    public void update(UserModal modal) {
        new UpdateUserAsyncTask(dao).execute(modal);
    }

    public void delete(UserModal modal) {
        new DeleteUserAsyncTask(dao).execute(modal);
    }

    public void deleteAllUsers() {
        new DeleteAllUserAsyncTask(dao).execute();
    }

    public LiveData<List<UserModal>> getAllUsers() {
        return allUsers;
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private Dao dao;
        private InsertUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... userModals) {
            dao.insert(userModals[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private Dao dao;
        private UpdateUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... userModals) {
            dao.update(userModals[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<UserModal, Void, Void> {
        private Dao dao;
        private DeleteUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserModal... userModals) {
            dao.delete(userModals[0]);
            return null;
        }
    }

    private static class DeleteAllUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void...voids) {
            dao.deleteAllUser();
            return null;
        }
    }
}
