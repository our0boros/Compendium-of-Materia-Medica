package com.example.compendiumofmateriamedica.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import model.Datastructure.Post;
import model.Datastructure.User;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<User> user;
    private final MutableLiveData<String> user_name;
    private final MutableLiveData<String> user_avatar;
    private final MutableLiveData<String> user_location;
    private final MutableLiveData<Integer> user_post;
    private final MutableLiveData<Integer> user_plants_discovered;

    public ProfileViewModel() {
        user = new MutableLiveData<>();
        user_name = new MutableLiveData<>();
        user_avatar = new MutableLiveData<>();
        user_location = new MutableLiveData<>();
        user_post = new MutableLiveData<>();
        user_plants_discovered = new MutableLiveData<>();
    }

    // Getter for observing
    public LiveData<User> getCurrentUser() {return user;}
    public LiveData<String> getUserName() {
        return user_name;
    }
    public LiveData<String> getUserAvatar() {
        return user_avatar;
    }
    public LiveData<String> getUserLocation() {
        return user_location;
    }
    public MutableLiveData<Integer> getUserPost() { return user_post;}
    public MutableLiveData<Integer> getUserPlantsDiscovered() {
        return user_plants_discovered;
    }


    // Method to update
    public void setCurrentUser(User user) { this.user.setValue(user);}
    public void updateUserName(String user_name){
        this.user_name.setValue(user_name);
    }
    public void updateUserAvatar(String user_avatar){
        this.user_avatar.setValue(user_avatar);
    }
    public void updateUserLocation(String user_location){this.user_location.setValue(user_location);
    }
    public void updateUserPost(List<Post> user_post_data){ this.user_post.setValue(user_post_data.size());}
    public void updateUserPlantDiscovered(int user_plants_discovered){ this.user_plants_discovered.setValue(user_plants_discovered);}

}

