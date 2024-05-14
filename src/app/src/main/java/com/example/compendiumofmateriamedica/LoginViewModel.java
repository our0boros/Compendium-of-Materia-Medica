package com.example.compendiumofmateriamedica;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseUser;

import model.Firebase.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository;
    private LiveData<FirebaseUser> userLiveData;

    public LoginViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<FirebaseUser> login(String email, String password) {
        userLiveData = userRepository.login(email, password);
        return userLiveData;
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}