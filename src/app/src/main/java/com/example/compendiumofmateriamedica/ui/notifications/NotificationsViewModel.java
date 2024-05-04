package com.example.compendiumofmateriamedica.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> username;

    public NotificationsViewModel() {
        username = new MutableLiveData<>();

    }

    public LiveData<String> getUsername() {
        return username;
    }
    public void updateUsername(String username){
        this.username.setValue(username);
    }
}