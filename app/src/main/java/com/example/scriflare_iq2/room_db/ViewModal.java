package com.example.scriflare_iq2.room_db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<UserModal>> allUsers;

    public ViewModal(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    public void insert(UserModal modal) {
        userRepository.insert(modal);
    }

    public void update(UserModal modal) {
        userRepository.update(modal);
    }

    public void delete(UserModal modal) {
        userRepository.delete(modal);
    }

    public void deleteAllUsers() {
        userRepository.deleteAllUsers();
    }

    public LiveData<List<UserModal>> getAllUsers() {
        return allUsers;
    }
}
