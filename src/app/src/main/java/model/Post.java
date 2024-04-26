package model;

import androidx.annotation.NonNull;

/**
 * @author: Haochen Gong
 * @description: Post类
 **/
public class Post implements Comparable<Post>{
    private final int postId;
    private final int uid;
    private final int plantId;
    private final String photo;
    private final String content;
    private final String timestamp;

    public Post(int postId, int uid, int plantId, String photo, String content, String timestamp){
        this.postId = postId;
        this.uid = uid;
        this.plantId = plantId;
        this.photo = photo;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getPostId() {
        return postId;
    }

    public int getUid() {
        return uid;
    }

    public int getPlantId() {
        return plantId;
    }

    public String getPhoto() {
        return photo;
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
        return "{PostID: " + postId + ", "
                + "UsrID: " + uid + ", "
                + "PlantID: " + plantId + ", "
                + "PhotoUrl: " + photo + ", "
                + "Content: " + content + ", "
                + "Time: " + timestamp + "}";
    }
}