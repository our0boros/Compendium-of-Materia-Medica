package com.example.compendiumofmateriamedica.ui.home;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import model.Post;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Post>> postsLiveData;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Class Social.");
        postsLiveData = new MutableLiveData<>();
        List<Post> initialPosts = new ArrayList<>();
        initialPosts.add(new Post(1,1, 53,"photo1", "Look what I found!", "2024-01-23"));
        initialPosts.add(new Post(2,2, 64,"photo2", "Wow!", "2024-03-12"));
        initialPosts.add(new Post(3,1, 9,"photo3", "This flower stinks!", "2024-05-01"));
        postsLiveData.setValue(initialPosts);
    }

    public LiveData<List<Post>> getPosts() {
        return postsLiveData;
    }
    public LiveData<String> getText(){
        return mText;
    }
}