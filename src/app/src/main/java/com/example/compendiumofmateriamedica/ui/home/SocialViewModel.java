package com.example.compendiumofmateriamedica.ui.home;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.compendiumofmateriamedica.MainActivity;

import java.util.ArrayList;
import java.util.HashSet;
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
        // 这里是初始化的数据
    public boolean isLoading = false;

    public HomeViewModel() {
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

        if (isLoading) {
            return;  // 如果已经在加载，则不进行新的加载
        }
        isLoading = true;  // 标记正在加载

        List<Post> currentPosts = postsLiveData.getValue();
        String lastTimestamp = null;
        if (currentPosts != null && !currentPosts.isEmpty()) {
            lastTimestamp = currentPosts.get(currentPosts.size() - 1).getTimestamp();
        }

        Log.d("HomeViewModel", "Loading more posts after timestamp: " + lastTimestamp);

        String finalLastTimestamp = lastTimestamp;
        MainActivity.getNewestPosts(number, lastTimestamp, new MainActivity.PostCallback() {
            @Override
            public void onCallback(List<Post> newPosts) {
                if (!newPosts.isEmpty() && newPosts.get(0).getTimestamp().equals(finalLastTimestamp)) {
                    newPosts.remove(0);  // Remove the first post if it is the same as the last one of the current posts
                }
                List<Post> currentPosts = postsLiveData.getValue();
                if (currentPosts == null) {
                    currentPosts = new ArrayList<>();
                }
                currentPosts.addAll(newPosts);
                postsLiveData.postValue(currentPosts); // 确保在主线程上更新 LiveData
                isLoading = false;
                Log.d("HomeViewModel", "Posts loaded, new count: " + currentPosts.size());
            }
        });
    }

}