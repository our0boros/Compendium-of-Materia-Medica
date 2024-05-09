package com.example.compendiumofmateriamedica;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;


/**
 * @author: Xing Chen
 * @description: 用于后台模拟通知消息
 *
 **/
public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private Timer timer;
    private Random random;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Notification service created.");
        random = new Random();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Notification service started.");
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Notification service destroyed.");
        stopTimer();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    // 定时模拟点赞行为
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                simulateLikes();
            }
        }, 0, 10000); // 每10秒执行一次
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    // 模拟点赞行为的逻辑
    private void simulateLikes() {
        Log.d(TAG, "Simulating likes...");
        // TODO 这里只需要获取当前app用户的posts就可以了，甚至只获取他最新的post就可以了
//        List<Post> posts = PostTreeManager.getInstance().getAllPosts();
//        for (Post post : posts) {
//            User author = UserTreeManager.getInstance().getUserById(post.getUser_id());
//            if (author.getUnreadNotificationCount() < 5) {
//                // 随机选择一个其他用户点赞
//                User randomUser = getRandomUser();
//                post.likedByUser(randomUser.getUser_id());
//                author.incrementUnreadNotificationCount();
//                Log.d(TAG, "User " + randomUser.getUser_id() + " liked post " + post.getPost_id());
//                EventBus.getDefault().post(new NewLikeEvent(post.getPost_id(), randomUser.getUser_id()));
//            }
//        }
    }

    private User getRandomUser() {
        // TODO 注意排除app用户自己
//        List<User> users = UserTreeManager.getInstance().getAllUsers();
//        int randomIndex = random.nextInt(users.size());
//        return users.get(randomIndex);
        return null;
    }
}
