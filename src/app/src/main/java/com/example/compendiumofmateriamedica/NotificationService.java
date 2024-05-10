package com.example.compendiumofmateriamedica;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import model.Datastructure.NewEvent;
import model.Datastructure.Post;
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
    private User currentUser;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Notification service created.");
        random = new Random();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Notification service started.");
        if(intent != null) {
            currentUser = (User) intent.getSerializableExtra("User");
            Log.d(TAG, "Get current user: " + currentUser.getUsername());
        } else {
            Log.d(TAG, "Get current user failed.");
        }
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
        }, 0, 10000); // 每10秒执行一次，这里1000对应1秒
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    // 模拟点赞行为的逻辑
    private void simulateLikes() {
        Post userNewestPost = MainActivity.getUserNewestPost(currentUser.getUser_id());
        User author = currentUser;
        if(userNewestPost != null){
            // 如果post被点赞小于5次，即用户被通知小于5次
            if (userNewestPost.getLikesRecord().size() < 6) {
                // 随机选择一个其他用户点赞
                User randomUser = getRandomUser();
                userNewestPost.likedByUser(randomUser.getUser_id());

                //记录当前时间
                Date now = new Date();

                Log.d(TAG, randomUser.getUsername() + " liked your post (Post id:" + userNewestPost.getPost_id() + ")");
                EventBus.getDefault().post(new NewEvent(userNewestPost.getPost_id(), randomUser, NewEvent.EventType.LIKE, now));
            }
        }
    }
    // 获取除了uid之外的其他随机一个用户
    private User getRandomUser() {
        List<User> users = UserTreeManager.getInstance().getAllUser();
        int randomIndex;
        do{
            randomIndex = random.nextInt(users.size());
        } while (users.get(randomIndex).getUser_id() == currentUser.getUser_id());
        return users.get(randomIndex);
    }
}
