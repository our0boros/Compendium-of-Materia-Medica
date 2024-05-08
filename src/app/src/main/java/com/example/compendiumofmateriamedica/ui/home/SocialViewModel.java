package com.example.compendiumofmateriamedica.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.compendiumofmateriamedica.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Post;

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

    public SocialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Class Social.");
        postsLiveData = new MutableLiveData<>(new ArrayList<>());
        loadMorePosts(10);
    }

    public LiveData<List<Post>> getPosts() {
        return postsLiveData;
    }
    public LiveData<String> getText(){
        return mText;
    }
    // load more post into current posts
    public void loadMorePosts(int number) {
/*        List<Post> currentPosts = postsLiveData.getValue();
        List<Post> newPosts = MainActivity.getRandomPosts(number);
        currentPosts.addAll(newPosts);
        postsLiveData.setValue(currentPosts); 我先注释了试NewestPost 看着用*/


        MainActivity.getNewestPosts(number, new MainActivity.PostCallback() {
            @Override
            public void onCallback(List<Post> newPosts) {
                List<Post> currentPosts = postsLiveData.getValue();
                if (currentPosts == null) {
                    currentPosts = new ArrayList<>();
                }
                currentPosts.addAll(newPosts);
                postsLiveData.postValue(currentPosts); // 确保在主线程上更新 LiveData
            }
        });
    }

}