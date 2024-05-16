package com.example.compendiumofmateriamedica;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import model.Datastructure.DataType;
import model.Datastructure.GeneratorFactory;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTree;
import model.Parser.Tokenizer;

public class PostTreeTest {
    private final Context context = ApplicationProvider.getApplicationContext();
    private RBTree<Post> postTree;
    private PostTreeManager postTreeManager;
    private final int VALID_UID = 5;
    private int has_post_number = 15;
    private final int VALID_PLANT_ID = 109482;
    private final int[] VALID_POST_ID = {5, 10, 100};
    private final String VALID_PHOTO_URL = "https://picsum.photos/id/109/640/480";
    private final String VALID_CONTENT = "Look what have I found";
    Tokenizer tokenizer = new Tokenizer(VALID_CONTENT);
    private final String VALID_TIMESTAMP = "2023-06-02T12:10:16.644093";
    private final int INVALID_UID = 99;
    private final int INVALID_PLANT_ID = 0;
    private final int[] INVALID_POST_ID = {0, 1001, 9999};

    @Before
    public void setUp() {
        try {
            postTree = (RBTree<Post>) GeneratorFactory.tree(context, DataType.POST, R.raw.posts);
            postTreeManager = PostTreeManager.getInstance(postTree);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(timeout = 1000)
    public void testValidPostShare(){
        for (int valid_postId : VALID_POST_ID) {
            Post valid_post = new Post(valid_postId, VALID_UID, VALID_PLANT_ID, VALID_PHOTO_URL, tokenizer.getFullToken(), VALID_TIMESTAMP);
            postTreeManager.insert(valid_postId, valid_post);
            has_post_number += 1;
            ArrayList<Post> searchResult = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(valid_postId));
            Assert.assertFalse("The post you share is not in post tree!", searchResult.isEmpty());
        }
    }

    @Test(timeout = 1000)
    public void testInvalidPostShare(){
        for (int invalid_postId : INVALID_POST_ID) {
            Post valid_post = new Post(invalid_postId, INVALID_UID, INVALID_PLANT_ID, VALID_PHOTO_URL, tokenizer.getFullToken(), VALID_TIMESTAMP);
            postTreeManager.insert(invalid_postId, valid_post);
            has_post_number += 1;
            ArrayList<Post> searchResult = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(invalid_postId));
            Assert.assertTrue("Invalid post shared successfully, which shouldn't do!", searchResult.isEmpty());
        }
    }

    @Test(timeout = 1000)
    public void testInsertionTreeSize(){
        for (int valid_postId : VALID_POST_ID) {
            Post valid_post = new Post(valid_postId, VALID_UID, VALID_PLANT_ID, VALID_PHOTO_URL, tokenizer.getFullToken(), VALID_TIMESTAMP);
            int sizeBefore = PostTreeManager.getInstance().getTreeSize();
            postTreeManager.insert(valid_postId, valid_post);
            has_post_number += 1;
            int sizeAfter = PostTreeManager.getInstance().getTreeSize();
            Assert.assertEquals("Size should be " + (sizeBefore + 1) + ", but get " + sizeAfter, sizeBefore + 1, sizeAfter);
        }
    }

    @Test(timeout = 1000)
    public void testGetPostsById(){
        has_post_number = postTreeManager.getPostsByUserId(VALID_UID).size();
        Assert.assertEquals("Wrong posts number before insertion.", has_post_number, postTreeManager.getPostsByUserId(VALID_UID).size());
        Post valid_post = new Post(VALID_POST_ID[1], VALID_UID, VALID_PLANT_ID, VALID_PHOTO_URL, tokenizer.getFullToken(), VALID_TIMESTAMP);
        postTreeManager.insert(VALID_POST_ID[1], valid_post);
        has_post_number += 1;
        Assert.assertEquals("Wrong posts number after insertion.", has_post_number, postTreeManager.getPostsByUserId(VALID_UID).size());
    }


}
