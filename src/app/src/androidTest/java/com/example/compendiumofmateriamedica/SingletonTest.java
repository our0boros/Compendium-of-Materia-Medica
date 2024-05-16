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

import model.Datastructure.DataType;
import model.Datastructure.GeneratorFactory;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTree;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;

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
        // 尝试使用反射创建新实例
        try {
            Constructor<UserTreeManager> constructor = UserTreeManager.class.getDeclaredConstructor(RBTree.class);
            constructor.setAccessible(true);
            UserTreeManager newInstance = constructor.newInstance(userTree);
            Assert.fail("UserTreeManager is not a singleton! New instance created via reflection.");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // 预期的行为，无法通过反射创建新实例
        }

        // 使用 getInstance 获取实例并比较
        UserTreeManager anotherInstance = UserTreeManager.getInstance(userTree);
        Assert.assertSame("UserTreeManager instances are not the same!", userTreeManager, anotherInstance);

        // 多线程环境下测试单例
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
        // 尝试使用反射创建新实例
        try {
            Constructor<PlantTreeManager> constructor = PlantTreeManager.class.getDeclaredConstructor(RBTree.class);
            constructor.setAccessible(true);
            PlantTreeManager newInstance = constructor.newInstance(plantTree);
            Assert.fail("PlantTreeManager is not a singleton! New instance created via reflection.");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // 预期的行为，无法通过反射创建新实例
        }

        // 使用 getInstance 获取实例并比较
        PlantTreeManager anotherInstance = PlantTreeManager.getInstance(plantTree);
        Assert.assertSame("PlantTreeManager instances are not the same!", plantTreeManager, anotherInstance);

        // 多线程环境下测试单例
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
}
