package com.example.compendiumofmateriamedica;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseUser;

import model.Firebase.LoginAuth;

/**
 * @autor: Yusi Zhong
 * @datetime: u7755061
 * @description: Manages user login and authentication state.
 */
public class LoginViewModel extends ViewModel {
    private LoginAuth loginAuth;
    private LiveData<FirebaseUser> userLiveData;

    public LoginViewModel() {
        loginAuth = new LoginAuth();
    }

    /**
     * @param email User's email address
     * @param password User's password
     * @return LiveData<FirebaseUser> LiveData object with the authenticated user
     */
    public LiveData<FirebaseUser> login(String email, String password) {
        userLiveData = loginAuth.login(email, password);
        return userLiveData;
    }

    /**
     * Method to get the current authenticated user as LiveData.
     * @return LiveData<FirebaseUser> LiveData object with the authenticated user
     */
    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}