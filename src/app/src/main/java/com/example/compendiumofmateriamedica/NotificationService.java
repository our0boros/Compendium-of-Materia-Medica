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
 * @uid: u7725171
 * @description: a backstage service to simulate periodically like and post action by other users
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
    private final int NOTIFICATION_PERIOD = 5000; // execute period, 1000 = 1 second
    private final int POST_PERIOD = 5000;
    private final int DELAY = 5000;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Notification service created.");
        random = new Random();
        postTreeManager = PostTreeManager.getInstance();
        jsonReader = new JsonReader(this);
        // initialize json data
        try {
            postsJsonList = jsonReader.readJsonFromFile(R.raw.posts_stream);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            postsJsonList = new ArrayList<>(); // prevent nullptr
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
    // set timer
    private void startTimer() {
        if (timer4like != null) {
            timer4like.cancel();  // cancel current timer
            timer4like = null;    // ensure garbage collection
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
    // simulate like
    private void simulateLikes() {
        Log.d(TAG, "Current user is :" + currentUser.getUsername());
        Post userNewestPost = postTreeManager.getUserNewestPost(currentUser.getUser_id());
        if(userNewestPost != null){
            // only execute when user newest post has less than 6 likes
            if (userNewestPost.getLikesRecord().size() < 6) {
                // choose a random user
                User randomUser = getRandomUser(userNewestPost);
                userNewestPost.likedByUser(randomUser.getUser_id());

                // get current time
                Date now = new Date();

                Log.d(TAG, randomUser.getUsername() + " liked your post (Post id:" + userNewestPost.getPost_id() + ")");
                Log.d(TAG, "Your post (Post id:" + userNewestPost.getPost_id() + ") has been liked by " + userNewestPost.getLikesRecord().size() + " users.");
                EventBus.getDefault().post(new NewEvent(userNewestPost.getPost_id(), randomUser, NewEvent.EventType.LIKE, now));
            }
        }
    }
    // simulate post
    private void simulateUserPosts(){
        // read a Post from json
        Post post = getPostFromJson();
        if(post != null){
            int postId = post.getPost_id();
            int postUid = post.getUser_id();
            // put this post into PostTree, post id as key
            postTreeManager.insert(postId, post);
            // notify changes
            // now
            Date now = new Date();
            Log.d(TAG, "User " + postUid + " shared a new post, post id " + postId);
            EventBus.getDefault().post(new NewEvent(postId, UserTreeManager.getInstance().findUserById(postUid), NewEvent.EventType.POST, now));
        }
    }
    // get a user except from current user
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
    // read posts from json one by one
    private Post getPostFromJson(){
        if (currentIndex >= postsJsonList.size()) {
            Log.d(TAG, "No more posts available in JSON.");
            return null;
        }

        JSONObject jsonObject = postsJsonList.get(currentIndex);
        currentIndex++; // update index

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

            // create new post
            Tokenizer tokenizer = new Tokenizer(content, true);
            Log.d("NotificationService", "Post read successfully.");
            return new Post(postId,uid,plantId,photo,tokenizer.getFullToken(),timestamp,likes,likesRecord,comments);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
