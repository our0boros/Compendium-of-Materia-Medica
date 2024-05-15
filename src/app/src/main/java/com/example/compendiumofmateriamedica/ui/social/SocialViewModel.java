package com.example.compendiumofmateriamedica.ui.social;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import model.Datastructure.NewEvent;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;

/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description: Use ViewModel to manage and prepare data for UI components,
 * survive configuration changes (like screen rotations),
 * and handle communication between the UI and the underlying data sources.
 */
public class SocialViewModel extends ViewModel {
    // Hold data that can be observed for changes
    private final MutableLiveData<List<Post>> postsLiveData;

    // Hold data that can be observed for 'like' changes
    private MutableLiveData<Set<Integer>> _likedPosts = new MutableLiveData<>();
    public LiveData<Set<Integer>> likedPosts = _likedPosts;

    ArrayList<Post> currentPost = null;

    // Constructor
    public SocialViewModel() {
        postsLiveData = new MutableLiveData<>(new ArrayList<>());
        loadMorePosts(10);
        // register on EventBus
        EventBus.getDefault().register(this);
    }

    // Getter for postsLiveData
    public LiveData<List<Post>> getPosts() {
        return postsLiveData;
    }

    // Load more post into current posts
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
    public void removePost(Post post) {
        List<Post> currentPosts = postsLiveData.getValue();
        if (currentPosts != null) {
            currentPosts.remove(post);
            postsLiveData.postValue(currentPosts);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewLikeEvent(NewEvent event) {
        // only deal with post
        if(event.getEventType() == NewEvent.EventType.POST){
            // 更新post列表
            Log.d("SocialViewModel", "Captured new post event.");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            String currentTimestamp = now.format(formatter);
            // add the newest post to current post
            currentPost.addAll(PostTreeManager.getInstance().getNewestPosts(1, currentTimestamp));
            // sort by descending order
            currentPost.sort(Comparator.comparingInt(Post::getPost_id).reversed());
            postsLiveData.postValue(currentPost);
        }
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        // unregister this from eventbus
        EventBus.getDefault().unregister(this);
    }
}