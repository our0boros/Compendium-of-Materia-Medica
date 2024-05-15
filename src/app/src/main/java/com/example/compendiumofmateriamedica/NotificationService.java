package com.example.compendiumofmateriamedica;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import model.Datastructure.JsonReader;
import model.Datastructure.NewEvent;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;
import model.Parser.Tokenizer;


/**
 * @author: Xing Chen
 * @description: 用于后台模拟通知消息
 *
 **/
public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private Timer timer4like;
    private Timer timer4post;
    private Random random;
    private User currentUser;
    private PostTreeManager postTreeManager;
    private JsonReader jsonReader;
    private List<JSONObject> postsJsonList;
    private int currentIndex = 0; // index to read post from json
    private final int NOTIFICATION_PERIOD = 5000; // 每10秒执行一次，这里1000对应1秒
    private final int POST_PERIOD = 5000;
    private final int DELAY = 5000;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Notification service created.");
        random = new Random();
        postTreeManager = PostTreeManager.getInstance();
        jsonReader = new JsonReader(this);
        // 初始化 JSON 数据
        try {
            postsJsonList = jsonReader.readJsonFromFile(R.raw.posts_stream);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            postsJsonList = new ArrayList<>(); // 防止空指针异常
        }
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
    // 定时模拟点赞和发布post行为
    private void startTimer() {
        if (timer4like != null) {
            timer4like.cancel();  // 取消当前运行的定时器任务
            timer4like = null;    // 将定时器设置为null，确保垃圾回收
        }
        if (timer4post != null) {
            timer4post.cancel();
            timer4post = null;
        }
        timer4like = new Timer();
        timer4like.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                simulateLikes();
            }
        }, DELAY, NOTIFICATION_PERIOD);
        timer4post = new Timer();
        timer4post.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                simulateUserPosts();
            }
        }, DELAY * 2, POST_PERIOD);
    }

    private void stopTimer() {
        if (timer4like != null) {
            timer4like.cancel();
            timer4like = null;
        }
        if (timer4post != null) {
            timer4post.cancel();
            timer4post = null;
        }
    }
    // 模拟点赞行为的逻辑
    private void simulateLikes() {
        Post userNewestPost = postTreeManager.getUserNewestPost(currentUser.getUser_id());
        User author = currentUser;
        if(userNewestPost != null){
            // 如果post被点赞小于5次，即用户被通知小于5次
            if (userNewestPost.getLikesRecord().size() < 6) {
                // 随机选择一个其他用户点赞
                User randomUser = getRandomUser(userNewestPost);
                userNewestPost.likedByUser(randomUser.getUser_id());

                //记录当前时间
                Date now = new Date();

                Log.d(TAG, randomUser.getUsername() + " liked your post (Post id:" + userNewestPost.getPost_id() + ")");
                Log.d(TAG, "Your post (Post id:" + userNewestPost.getPost_id() + ") has been liked by " + userNewestPost.getLikesRecord().size() + " users.");
                EventBus.getDefault().post(new NewEvent(userNewestPost.getPost_id(), randomUser, NewEvent.EventType.LIKE, now));
            }
        }
    }
    // 模拟用户发布Post的逻辑
    private void simulateUserPosts(){
        // read a Post from json
        Post post = getPostFromJson();
        if(post != null){
            int postId = post.getPost_id();
            int postUid = post.getUser_id();
            // put this post into PostTree, post id as key
            postTreeManager.insert(postId, post);
            // notify changes
            //记录当前时间
            Date now = new Date();
            Log.d(TAG, "User " + postUid + " shared a new post, post id " + postId);
            EventBus.getDefault().post(new NewEvent(postId, UserTreeManager.getInstance().findUserById(postUid), NewEvent.EventType.POST, now));
        }
    }
    // 获取除了uid之外的其他随机一个用户
    private User getRandomUser(Post post) {
        List<User> users = UserTreeManager.getInstance().getAllUser();
        int randomIndex;
        // find a random index that is not current user and not those who have already liked the post
        do{
            randomIndex = random.nextInt(users.size());
        } while (users.get(randomIndex).getUser_id() == currentUser.getUser_id()
                || post.wasLikedByUser(users.get(randomIndex).getUser_id()));
        return users.get(randomIndex);
    }
    // 从JSON文件中依次读取出一个post
    private Post getPostFromJson(){
        if (currentIndex >= postsJsonList.size()) {
            Log.d(TAG, "No more posts available in JSON.");
            return null;
        }

        JSONObject jsonObject = postsJsonList.get(currentIndex);
        currentIndex++; // 更新索引，确保下次读取时读取下一个

        try {
            int postId = PostTreeManager.getInstance().getTreeSize() + 1;
            int uid = jsonObject.getInt("user_id");
            if (uid == currentUser.getUser_id()) {
                // current user should not post
                return getPostFromJson();
            }
            int plantId = jsonObject.getInt("plant_id");
            String photo = jsonObject.getString("photo_url");
            String content = jsonObject.getString("content");
            // timestamp is now
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
            Date now = new Date();
            String timestamp = sdf.format(now);

            List<Integer> likes = new ArrayList<>();
            List<Integer> likesRecord = new ArrayList<>();
            Map<Integer, String> comments = new LinkedHashMap<>();

            // 创建并插入节点
            Tokenizer tokenizer = new Tokenizer(content);
            Log.d("NotificationService", "Post read successfully.");
            return new Post(postId,uid,plantId,photo,tokenizer.getFullToken(),timestamp,likes,likesRecord,comments);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
