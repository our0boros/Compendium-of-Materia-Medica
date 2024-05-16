package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTree;
import model.Parser.Token;

public class PostTreeManagerTest {
    private RBTree<Post> postTree;
    private PostTreeManager postTreeManager;

    @Before
    public void setUp() {
        postTree = new RBTree<>();
        postTreeManager = PostTreeManager.getInstance(postTree);

        Post post1 = new Post(1, 101, 201, "photo1.png",
                List.of(new Token("This is a post about plants", Token.Type.TEXT)),
                "2023-01-01T10:00:00", List.of(1, 2), List.of(1), new LinkedHashMap<>(Map.of(1, "Nice post!")));

        Post post2 = new Post(2, 102, 202, "photo2.png",
                List.of(new Token("Another post about different plants", Token.Type.TEXT)),
                "2023-02-01T10:00:00", List.of(3, 4), List.of(2), new LinkedHashMap<>(Map.of(2, "Great!")));

        Post post3 = new Post(3, 101, 203, "photo3.png",
                List.of(new Token("Yet another post about plants", Token.Type.TEXT)),
                "2023-03-01T10:00:00", List.of(5, 6), List.of(3), new LinkedHashMap<>(Map.of(3, "Interesting!")));

        postTreeManager.insert(post1.getPost_id(), post1);
        postTreeManager.insert(post2.getPost_id(), post2);
        postTreeManager.insert(post3.getPost_id(), post3);
    }

    @Test
    public void testInsert() {
        Post newPost = new Post(4, 103, 204, "photo4.png",
                List.of(new Token("A new post about plants", Token.Type.TEXT)),
                "2023-04-01T10:00:00", List.of(7, 8), List.of(4), new LinkedHashMap<>(Map.of(4, "Nice!")));

        postTreeManager.insert(newPost.getPost_id(), newPost);

        ArrayList<Post> result = postTreeManager.search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(newPost.getPost_id()));
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(newPost.getPost_id(), result.get(0).getPost_id());
    }

    @Test
    public void testDelete() {
        postTreeManager.delete(1);
        ArrayList<Post> result = postTreeManager.search(PostTreeManager.PostInfoType.POST_ID, "1");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchByUserId() {
        ArrayList<Post> posts = postTreeManager.search(PostTreeManager.PostInfoType.UID, "101");
        assertEquals(2, posts.size());
    }

    @Test
    public void testSearchByPlantId() {
        ArrayList<Post> posts = postTreeManager.search(PostTreeManager.PostInfoType.PLANT_ID, "202");
        assertEquals(1, posts.size());
        assertEquals(202, posts.get(0).getPlant_id());
    }

    @Test
    public void testSearchByTime() {
        ArrayList<Post> posts = postTreeManager.search(PostTreeManager.PostInfoType.TIME, "2023-01-01T10:00:00");
        assertEquals(1, posts.size());
        assertEquals("2023-01-01T10:00:00", posts.get(0).getTimestamp());
    }

    @Test
    public void testSearchByContent() {
        ArrayList<Post> posts = postTreeManager.search(PostTreeManager.PostInfoType.CONTENT, "plants");
        assertEquals(3, posts.size());
    }

    @Test
    public void testGetNewestPosts() {
        ArrayList<Post> newestPosts = postTreeManager.getNewestPosts(2, "2023-03-01T10:00:00");
        assertEquals(2, newestPosts.size());
        assertEquals(3, newestPosts.get(0).getPost_id());
        assertEquals(2, newestPosts.get(1).getPost_id());
    }

    @Test
    public void testGetPostsByUserId() {
        List<Post> posts = postTreeManager.getPostsByUserId(101);
        assertEquals(2, posts.size());
    }

    @Test
    public void testGetUserPlantDiscovered() {
        Set<Integer> plantsDiscovered = postTreeManager.getUserPlantDiscovered(101);
        assertEquals(2, plantsDiscovered.size());
        assertTrue(plantsDiscovered.contains(201));
        assertTrue(plantsDiscovered.contains(203));
    }

    @Test
    public void testGetUserNewestPost() {
        Post newestPost = postTreeManager.getUserNewestPost(101);
        assertNotNull(newestPost);
        assertEquals(3, newestPost.getPost_id());
    }

    @Test
    public void testSingleton() {
        PostTreeManager anotherInstance = PostTreeManager.getInstance(postTree);
        assertSame(postTreeManager, anotherInstance);
    }

    @Test
    public void testGetTypeByString() throws Exception {
        assertEquals(PostTreeManager.PostInfoType.POST_ID, postTreeManager.getTypeByString("POST_ID"));
        assertEquals(PostTreeManager.PostInfoType.UID, postTreeManager.getTypeByString("UID"));
    }

    @Test(expected = Exception.class)
    public void testGetTypeByStringInvalid() throws Exception {
        postTreeManager.getTypeByString("INVALID_TYPE");
    }

}
