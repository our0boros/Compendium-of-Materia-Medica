package com.example.compendiumofmateriamedica.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> username;

    public ProfileViewModel() {
        username = new MutableLiveData<>();

    }

    public LiveData<String> getUsername() {
        return username;
    }
    public void updateUsername(String username){
        this.username.setValue(username);
    }
}

