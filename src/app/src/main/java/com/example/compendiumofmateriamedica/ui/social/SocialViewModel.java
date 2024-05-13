package com.example.compendiumofmateriamedica.ui.social;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.compendiumofmateriamedica.MainActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;

/**
 * @author: Hongjun Xu, Xing Chen
 * @datetime: 2024/5/2
 * @description: A ViewModel to control the datastream.
 */
public class SocialViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Post>> postsLiveData;
    // 点赞相关
    private MutableLiveData<Set<Integer>> _likedPosts = new MutableLiveData<>();
    public LiveData<Set<Integer>> likedPosts = _likedPosts;
    ArrayList<Post> currentPost = null;

    public SocialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Scroll down to see more.");
        postsLiveData = new MutableLiveData<>(new ArrayList<>());
        loadMorePosts(10);
    }

    public LiveData<List<Post>> getPosts() {
        return postsLiveData;
    }

    public LiveData<String> getText() {
        return mText;
    }

    // load more post into current posts
    public void loadMorePosts(int number) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        String currentTimestamp = now.format(formatter);
        if (currentPost == null) {
            currentPost = PostTreeManager.getInstance().getNewestPosts(number, currentTimestamp);
        } else {
            String nextTimestamp = currentPost.get(currentPost.size()-1).getTimestamp();
            currentPost.addAll(PostTreeManager.getInstance().getNewestPosts(number, nextTimestamp));
        }
        postsLiveData.postValue(currentPost);
    }
}