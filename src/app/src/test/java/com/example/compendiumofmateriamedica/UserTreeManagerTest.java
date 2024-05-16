package com.example.compendiumofmateriamedica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import model.Datastructure.RBTree;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;


/**
 * @author Haochen Gong u7776634
 * UserTreeManager class test
 **/
public class UserTreeManagerTest {

    private RBTree<User> userTree;
    private UserTreeManager userTreeManager;
    @Before
    public void setUp() {
        userTree = new RBTree<>();
        userTreeManager = UserTreeManager.getInstance(userTree);

        User user1 = new User(1, "Alice", "alice@example.com", "alice123","avatar1.png");
        User user2 = new User(2, "Bob", "bob@example.com", "bob123", "avatar2.png");
        User user3 = new User(3, "Charlie", "charlie@example.com", "charlie123", "avatar3.png");

        userTreeManager.insert(user1.getUser_id(), user1);
        userTreeManager.insert(user2.getUser_id(), user2);
        userTreeManager.insert(user3.getUser_id(), user3);
    }


    @Test
    public void testInsert() {
        User newUser = new User(4, "David", "david@example.com", "david123", "avatar4.png");
        userTreeManager.insert(newUser.getUser_id(), newUser);
        User result = userTreeManager.search(UserTreeManager.UserInfoType.ID, newUser.getUser_id()).get(0);
        assertNotNull(result);
        assertEquals(newUser.getUsername(), result.getUsername());
    }

    @Test
    public void testDelete() {
        userTreeManager.delete(1);
        ArrayList<User> result = userTreeManager.search(UserTreeManager.UserInfoType.ID,1);
        assertEquals(0, result.size());
    }

    @Test
    public void testSearchByID() {
        ArrayList<User> users = userTreeManager.search(UserTreeManager.UserInfoType.ID, 3);
        assertEquals(1, users.size());
        assertEquals("Charlie", users.get(0).getUsername());
    }

    @Test
    public void testSearchByName() {
        ArrayList<User> users = userTreeManager.search(UserTreeManager.UserInfoType.NAME, "Charlie");
        assertEquals(1, users.size());
        assertEquals("Charlie", users.get(0).getUsername());
    }

    @Test
    public void testSearchByEmail() {
        ArrayList<User> users = userTreeManager.search(UserTreeManager.UserInfoType.EMAIL, "charlie@example.com");
        assertEquals(1, users.size());
        assertEquals("Charlie", users.get(0).getUsername());
    }

    @Test
    public void testSearchByAvatar() {
        ArrayList<User> users = userTreeManager.search(UserTreeManager.UserInfoType.AVATAR, "avatar3.png");
        assertEquals(1, users.size());
        assertEquals("Charlie", users.get(0).getUsername());
    }

    @Test
    public void testGetAllUser() {
        List<User> users = userTreeManager.getAllUser();
        assertEquals(3, users.size());
        assertEquals("Alice", users.get(0).getUsername());
        assertEquals("Bob", users.get(1).getUsername());
        assertEquals("Charlie", users.get(2).getUsername());
    }

    @Test
    public void testFindUserById() {
        User user = userTreeManager.findUserById(1);
        assertNotNull(user);
        assertEquals(user.getUsername(), "Alice");
    }

    @Test
    public void testSingleton() {
        UserTreeManager anotherInstance = UserTreeManager.getInstance();
        assertSame(userTreeManager, anotherInstance);
    }
}
