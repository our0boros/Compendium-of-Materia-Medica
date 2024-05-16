package com.example.compendiumofmateriamedica;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import model.Adapters.NotificationAdapter;
import model.Datastructure.DataType;
import model.Datastructure.GeneratorFactory;
import model.Datastructure.NewEvent;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTree;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;
/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description: Test if TreeManagers and NotificationAdapter are singleton.
 */
public class SingletonTest {
    private final Context context = ApplicationProvider.getApplicationContext();
    private RBTree<Post> postTree;
    private RBTree<User> userTree;
    private RBTree<Plant> plantTree;
    private PostTreeManager postTreeManager;
    private UserTreeManager userTreeManager;
    private PlantTreeManager plantTreeManager;
    @Before
    public void setUp() {
        try {
            postTree = (RBTree<Post>) GeneratorFactory.tree(context, DataType.POST, R.raw.posts);
            userTree = (RBTree<User>) GeneratorFactory.tree(context, DataType.USER, R.raw.users);
            plantTree = (RBTree<Plant>) GeneratorFactory.tree(context, DataType.PLANT, R.raw.plants);

            postTreeManager = PostTreeManager.getInstance(postTree);
            userTreeManager = UserTreeManager.getInstance(userTree);
            plantTreeManager = PlantTreeManager.getInstance(plantTree);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(timeout = 1000)
    public void testPostTreeManagerSingleton(){
        // Try using reflection to create a new instance
        try {
            Constructor<PostTreeManager> constructor = PostTreeManager.class.getDeclaredConstructor(RBTree.class);
            constructor.setAccessible(true);
            PostTreeManager newInstance = constructor.newInstance(postTree);
            Assert.fail("PostTreeManager is not a singleton! New instance created via reflection.");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            // Expected behavior, new instance cannot be created via reflection
        }

        // Use getInstance to get the instance and compare
        PostTreeManager anotherInstance = PostTreeManager.getInstance(postTree);
        Assert.assertSame("PostTreeManager instances are not the same!", postTreeManager, anotherInstance);

        // Test singleton in multi-threaded environment
        PostTreeManager[] instanceFromThreads = new PostTreeManager[2];
        Thread thread1 = new Thread(() -> instanceFromThreads[0] = PostTreeManager.getInstance(postTree));
        Thread thread2 = new Thread(() -> instanceFromThreads[1] = PostTreeManager.getInstance(postTree));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertSame("PostTreeManager instances from different threads are not the same!", instanceFromThreads[0], instanceFromThreads[1]);
    }

    @Test(timeout = 1000)
    public void testUserTreeManagerSingleton() {
        // Try using reflection to create a new instance
        try {
            Constructor<UserTreeManager> constructor = UserTreeManager.class.getDeclaredConstructor(RBTree.class);
            constructor.setAccessible(true);
            UserTreeManager newInstance = constructor.newInstance(userTree);
            Assert.fail("UserTreeManager is not a singleton! New instance created via reflection.");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // Expected behavior, new instance cannot be created via reflection
        }

        // Use getInstance to get the instance and compare
        UserTreeManager anotherInstance = UserTreeManager.getInstance(userTree);
        Assert.assertSame("UserTreeManager instances are not the same!", userTreeManager, anotherInstance);

        // Test singleton in multi-threaded environment
        UserTreeManager[] instanceFromThreads = new UserTreeManager[2];
        Thread thread1 = new Thread(() -> instanceFromThreads[0] = UserTreeManager.getInstance(userTree));
        Thread thread2 = new Thread(() -> instanceFromThreads[1] = UserTreeManager.getInstance(userTree));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertSame("UserTreeManager instances from different threads are not the same!", instanceFromThreads[0], instanceFromThreads[1]);
    }

    @Test(timeout = 1000)
    public void testPlantTreeManagerSingleton() {
        // Try using reflection to create a new instance
        try {
            Constructor<PlantTreeManager> constructor = PlantTreeManager.class.getDeclaredConstructor(RBTree.class);
            constructor.setAccessible(true);
            PlantTreeManager newInstance = constructor.newInstance(plantTree);
            Assert.fail("PlantTreeManager is not a singleton! New instance created via reflection.");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // Expected behavior, new instance cannot be created via reflection
        }

        // Use getInstance to get the instance and compare
        PlantTreeManager anotherInstance = PlantTreeManager.getInstance(plantTree);
        Assert.assertSame("PlantTreeManager instances are not the same!", plantTreeManager, anotherInstance);

        // Test singleton in multi-threaded environment
        PlantTreeManager[] instanceFromThreads = new PlantTreeManager[2];
        Thread thread1 = new Thread(() -> instanceFromThreads[0] = PlantTreeManager.getInstance(plantTree));
        Thread thread2 = new Thread(() -> instanceFromThreads[1] = PlantTreeManager.getInstance(plantTree));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertSame("PlantTreeManager instances from different threads are not the same!", instanceFromThreads[0], instanceFromThreads[1]);
    }

    @Test(timeout = 1000)
    public void testNotificationAdapterSingleton() {
        List<NewEvent> notifications = new ArrayList<>();
        // Try using reflection to create a new instance
        try {
            Constructor<NotificationAdapter> constructor = NotificationAdapter.class.getDeclaredConstructor(Context.class, List.class);
            constructor.setAccessible(true);
            NotificationAdapter newInstance = constructor.newInstance(context, notifications);
            Assert.fail("NotificationAdapter is not a singleton! New instance created via reflection.");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // Expected behavior, new instance cannot be created via reflection
        }

        // Use getInstance to get the instance and compare
        NotificationAdapter anotherInstance = NotificationAdapter.getInstance(context, notifications);
        Assert.assertSame("NotificationAdapter instances are not the same!", NotificationAdapter.getInstance(), anotherInstance);

        // Test singleton in multi-threaded environment
        NotificationAdapter[] instanceFromThreads = new NotificationAdapter[2];
        Thread thread1 = new Thread(() -> instanceFromThreads[0] = NotificationAdapter.getInstance(context, notifications));
        Thread thread2 = new Thread(() -> instanceFromThreads[1] = NotificationAdapter.getInstance(context, notifications));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertSame("NotificationAdapter instances from different threads are not the same!", instanceFromThreads[0], instanceFromThreads[1]);
    }

}
