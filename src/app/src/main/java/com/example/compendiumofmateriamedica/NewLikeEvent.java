package com.example.compendiumofmateriamedica;

import model.Datastructure.User;

public class NewLikeEvent {
    private int postId;
    private User liker;

    public NewLikeEvent(int postId, User liker) {
        this.postId = postId;
        this.liker = liker;
    }

    public int getPostId() {
        return postId;
    }

    public User getLiker() {
        return liker;
    }
}
