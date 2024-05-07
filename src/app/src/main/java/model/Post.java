package model;

import androidx.annotation.NonNull;

/**
 * @author: Haochen Gong
 * @description: Post类
 **/
public class Post implements Comparable<Post>{
    // Start with 1
    private int post_id;
    private int user_id;
    private int plant_id;
    private String photo_url;
    private String content;
    private String timestamp;

    public Post(){}

    public Post(int postId, int uid, int plantId, String photo, String content, String timestamp){
        this.post_id = postId;
        this.user_id = uid;
        this.plant_id = plantId;
        this.photo_url = photo;
        this.content = content;
        this.timestamp = timestamp;
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

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
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
                + "Time: " + timestamp + "}";
    }
}