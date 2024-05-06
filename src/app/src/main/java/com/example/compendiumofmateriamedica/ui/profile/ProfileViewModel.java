package com.example.compendiumofmateriamedica.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import model.Post;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> user_name;
    private final MutableLiveData<String> user_location;
    private final MutableLiveData<Integer> user_post;

    public ProfileViewModel() {
        user_name = new MutableLiveData<>();
        user_location = new MutableLiveData<>();
        user_post = new MutableLiveData<>();
    }

    public LiveData<String> getUserName() {
        return user_name;
    }
    public LiveData<String> getUserLocation() {
        return user_location;
    }
    public MutableLiveData<Integer> getUserPost() { return user_post;}

    public void updateUserName(String user_name){
        this.user_name.setValue(user_name);
    }
    public void updateUserLocation(String user_location){this.user_location.setValue(user_location);
    }
    public void updateUserPost(List<Post> user_post_data){ this.user_post.setValue(user_post_data.size());}

}

