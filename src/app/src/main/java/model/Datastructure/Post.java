package model.Datastructure;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import model.Parser.Token;

/**
 * @author Haochen Gong
 * Post class
 **/
public class Post implements Comparable<Post>{
    // Start with 1
    private int post_id;
    private int user_id;
    private int plant_id;
    private String photo_url;
    private List<Token> content;
    private String timestamp;
    // user id who liked this post
    private List<Integer> likes;
    private List<Integer> likesRecord;
    private Map<Integer, String> comments;

    private final ReentrantLock lock = new ReentrantLock();

    public Post(){}

    public Post(int postId, int uid, int plantId, String photo, List<Token> content, String timestamp){
        this.post_id = postId;
        this.user_id = uid;
        this.plant_id = plantId;
        this.photo_url = photo;
        this.content = content;
        this.timestamp = timestamp;
        this.likes = new ArrayList<>();
        this.likesRecord = new ArrayList<>();
        this.comments = new LinkedHashMap<>();
    }
    public Post(int postId, int uid, int plantId, String photo, List<Token> content, String timestamp, List<Integer> likes, List<Integer> likesRecord, Map<Integer, String> comments){
        this.post_id = postId;
        this.user_id = uid;
        this.plant_id = plantId;
        this.photo_url = photo;
        this.content = content;
        this.timestamp = timestamp;
        this.likes = likes;
        this.likesRecord = likesRecord;
        this.comments = comments;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public List<Token> getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<Integer> getLikes() { return likes; }

    public List<Integer> getLikesRecord() { return likesRecord; }

    public Map<Integer, String> getComments() { return comments; }

    public Object getByType(PostTreeManager.PostInfoType type) {
        switch (type) {
            case POST_ID: return getPost_id();
            case UID: return getUser_id();
            case PLANT_ID: return getPlant_id();
            case TIME: return getTimestamp();
            case CONTENT: return getContent();
            default: return null;
        }
    }
    // determine if post is liked by user
    public boolean isLikedByUser(int uid){
        return this.likes.contains(uid);
    }
    // determine if post was liked by user
    public boolean wasLikedByUser(int uid){
        return this.likesRecord.contains(uid);
    }
    // called when user press like button
    public void likedByUser(int uid){
        if (isLikedByUser(uid)) {
            // if already liked
            // cancel
            this.likes.remove(Integer.valueOf(uid)); // use Integer.valueOf(uid) here means it is not index
        } else {
            // if not liked
            // like
            this.likes.add(uid);
            // if now liked before
            if(!wasLikedByUser(uid)){
                likesRecord.add(uid);
            }
            // if was liked before, do nothing
        }
    }

    @Override
    public int compareTo(Post post) {
        return this.timestamp.compareTo(post.timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "{PostID: " + post_id + ", "
                + "UsrID: " + user_id + ", "
                + "PlantID: " + plant_id + ", "
                + "PhotoUrl: " + photo_url + ", "
                + "Content: " + content + ", "
                + "Time: " + timestamp + ", "
                + "Likes: " + likes.toString() + ", "
                + "Comments: " + comments.toString() + "}";
    }
}