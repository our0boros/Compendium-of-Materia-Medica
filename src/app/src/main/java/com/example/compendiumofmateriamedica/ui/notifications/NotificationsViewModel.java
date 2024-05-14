package com.example.compendiumofmateriamedica.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// ViewModel for managing notifications data
public class NotificationsViewModel extends ViewModel {

    // MutableLiveData to hold the username
    private final MutableLiveData<String> username;

    // Constructor to initialize MutableLiveData
    public NotificationsViewModel() {
        // Initialize MutableLiveData
        username = new MutableLiveData<>();
    }

    // Method to observe changes in the username LiveData
    public LiveData<String> getUsername() {
        // Expose LiveData to observe username changes
        return username;
    }

    // Method to update the username value
    public void updateUsername(String username) {
        // Set the new username value
        this.username.setValue(username);
    }
}
