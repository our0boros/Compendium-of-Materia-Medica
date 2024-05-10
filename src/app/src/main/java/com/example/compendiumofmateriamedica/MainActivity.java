package com.example.compendiumofmateriamedica;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.compendiumofmateriamedica.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.Datastructure.NewEvent;
import model.Datastructure.NewEventHandler;
import model.Datastructure.Plant;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NewEventHandler eventHandler;

    // 因为用了单例模式，LoginActivity已经实例化了，这些不需要了
//    public RBTree<User> userTree;
//    public RBTree<Plant> plantTree;
//    public RBTree<Post> postTree;
//
//    private UserTreeManager userTreeManager;
//    private PostTreeManager postTreeManager;
//    private PlantTreeManager plantTreeManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventHandler = NewEventHandler.getInstance();
        // 因为用了单例模式，LoginActivity已经实例化了，这些不需要了
//        // 运行加载数据的函数
//        try {
//            DataInitial();
//        } catch (JSONException | IOException e) {
//            throw new RuntimeException(e);
//        }

//        // 初始化TreeManagers
//        userTreeManager = UserTreeManager.instance;
//        postTreeManager = PostTreeManager.instance;
//        plantTreeManager = PlantTreeManager.instance;


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_social, R.id.navigation_camera, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // 隐藏顶部那个活动栏目
        this.getSupportActionBar().hide();
    }
    @Override
    protected void onStart() {
        super.onStart();
        // 启动通知服务
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("User", this.getIntent().getSerializableExtra("User"));
        startService(intent);
//        // 注册 EventBus
//        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
//        // 停止通知服务
//        Intent intent = new Intent(this, NotificationService.class);
//        stopService(intent);
//        // 注销 EventBus
//        EventBus.getDefault().unregister(this);
    }

//    /**
//     * 收到通知时间时，显示通知
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onNewLikeEvent(NewEvent event) {
//        // 显示点赞通知
//        showNotification(event.getEventUser().getUsername() + " liked your post (Post id: " + event.getPostId() + ")");
//        // 添加时间到EventHandler中
//        eventHandler.addEvent(event);
//    }

//    /**
//     * 显示一个弹窗页面
//     * @param message
//     */
//    private void showNotification(String message) {
//        // 在这里显示顶部弹出窗口,显示点赞通知
//        // 可以使用 Toast 或自定义的弹窗来显示
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        // 检查通道是否已创建
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // 请求权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
//        }
//        // 已经有权限，可以创建通知渠道
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID", "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(channel);
//        }
//        Notification notification = new NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
//                .setContentTitle("New Like")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.button_post_unlike)
//                .setAutoCancel(true)
//                .build();
//        notificationManager.notify(1, notification); // 1是这个通知的唯一ID
//    }
    // 从其他activity跳转回来时，检测有没有要求跳转到指定fragment
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 可能在活动已经启动时接收到新的 Intent
        // 如果是从PostShare页面过来的，那么默认返回到Social Fragment页面
        if (intent.hasExtra("navigate_fragment_id")) {
            int fragmentId = intent.getIntExtra("navigate_fragment_id", 0);
            if (fragmentId != 0) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
                navController.navigate(fragmentId);
            }
        }
        // 返回Social页面后，刷新页面以显示用户刚刚发布的Post
        // TODO

    }

    // 因为用了单例模式，LoginActivity已经实例化了，这些不需要了
//    /**
//     * @author: Haochen Gong
//     * @description: 加载数据
//     **/
//    private void DataInitial() throws JSONException, IOException {
//        userTree = (RBTree<User>) GeneratorFactory.tree(this, DataType.USER, R.raw.users);
//        plantTree = (RBTree<Plant>) GeneratorFactory.tree(this, DataType.PLANT, R.raw.plants);
//        postTree = (RBTree<Post>) GeneratorFactory.tree(this, DataType.POST, R.raw.posts);
//    }

//    /**
//     * 获取当前 Activity 下的Tree变量
//     * @return
//     */
//    public RBTree<Plant> getPlantTree() {
//        return plantTree;
//    }
//
//    public RBTree<Post> getPostTree() {
//        return postTree;
//    }
//
//    public RBTree<User> getUserTree() {
//        return userTree;
//    }

    /**
     * 根据uid找用户的用户名和头像
     * 下面的方法暂时用于在PostAdapter中，在social界面显示用户名和头像
     */
    public static User findUserById(int uid){
        if (UserTreeManager.getInstance() == null) {
            Log.w("UserTreeManager", "UserTreeManager未初始化");
            return null;
        }
        // 调用userTreeManager的搜索方法来找用户
        ArrayList<RBTreeNode<User>> users = UserTreeManager.getInstance().search(UserTreeManager.UserInfoType.ID, uid);

        // 因为UID都是唯一的，所以我们的users中应当只有一个user
        if (!users.isEmpty()) {
            User foundUser = users.get(0).getValue();
            Log.d("UserTreeManager", "找到用户: " + foundUser.getUsername());
            return foundUser;
        } else {
            Log.w("UserTreeManager", "没有找到用户.");
            return null;
        }
    }

    /**
     * 根据URL获取图像并设置为对应的imageView的图像
     * @param url
     * @param imageView
     */
    public static void loadImageFromURL(Context context, String url, ImageView imageView, String imageType){
        int errorImage;

        switch (imageType) {
            case "Avatar":
                errorImage = R.drawable.unknown_user;
                break;
            case "Photo":
                errorImage = R.drawable.load_image_fail;
                break;
            default:
                errorImage = R.drawable.load_image_fail;
                break;
        }
        Glide.with(context)
             .load(url)
             .placeholder(R.drawable.loading_image) // 加载中的占位图
             .error(errorImage) // 加载失败的错误图
             .into(imageView);
    }

    /**
     * 通过postID获取post
     * @param postId
     * @return
     */
    public static Post getPostByPostId(int postId) {
        ArrayList<RBTreeNode<Post>> searchResult = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(postId));
        if (!searchResult.isEmpty()) {
            return searchResult.get(0).getValue();
        }
        return null;
    }

    /**
     * 根据指定的数量随机获取指定数量的post，并以list的形式返回
     * @param numberOfPosts
     * @return
     */
    public static List<Post> getRandomPosts(int numberOfPosts) {
        List<Post> randomPosts = new ArrayList<>();
        //TODO 读取最近的十个post，目前只是在50个post中随机读取10个,另外如果50个post都读完了会发生什么还没处理
        List<Integer> allPostIds = generateUniqueRandomNumbers(numberOfPosts,1,50);
        Collections.shuffle(allPostIds);

        for (int i = 0; i < numberOfPosts && i < allPostIds.size(); i++) {
            int randomPostId = allPostIds.get(i);
            Post post = getPostByPostId(randomPostId);
            if (post != null) {
                randomPosts.add(post);
            }
        }

        return randomPosts;
    }

    public interface PostCallback {
        void onCallback(List<Post> posts);
    }
    public static List<Post> getNewestPosts(int numberOfPosts, String lastLoadedPostTimestamp, PostCallback callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postsRef = database.getReference("posts");

        if (lastLoadedPostTimestamp == null) {
            // 如果没有提供时间戳，加载最新的 posts
            // 查询并排序，获取最新的 10 个 posts
            postsRef.orderByChild("timestamp").limitToLast(numberOfPosts)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Post> newestPosts = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Post post = snapshot.getValue(Post.class);
                                newestPosts.add(post);
                                // Log each post's timestamp for debugging
                                Log.d("LoadPosts", "Post loaded: ID=" + post.getPost_id() + ", Timestamp=" + post.getTimestamp());
                            }
                            // 将newestPosts 列表反转，因为返回的数据默认是时间升序
                            Collections.reverse(newestPosts);
                            callback.onCallback(newestPosts);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                        }
                    });
        } else {
            // 加载时间戳之前的 posts
            postsRef.orderByChild("timestamp").endAt(lastLoadedPostTimestamp).limitToLast(numberOfPosts)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Post> newestPosts = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Post post = snapshot.getValue(Post.class);
                                newestPosts.add(post);
                            }
                            // 将newestPosts 列表反转，因为返回的数据默认是时间升序
                            Collections.reverse(newestPosts);
                            callback.onCallback(newestPosts);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                        }
                    });
        }



       return null;
    }
    public static List<Integer> getNewestPostsId(int numberOfPosts){

        return null;
    }
    // get all posts by user with uid
    public static List<Post> getPostsByUserId(int uid){
        ArrayList<RBTreeNode<Post>>  user_post_data = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.UID, String.valueOf(uid));
        List<Post> user_post_data_list= new ArrayList<>();
        if (!user_post_data.isEmpty()) {
            for (RBTreeNode<Post> node : user_post_data) {
                user_post_data_list.add(node.getValue());
            }
            return user_post_data_list;
        }
        Log.w("MainActivity", "Get posts by user id" + uid + " failed, there is no posts of this user");
        return new ArrayList<>(); // 防止出现null指针
    }
    public static Set<Integer> getUserPlantDiscovered(int uid){
        List<Post> posts = getPostsByUserId(uid);
        Set<Integer> plantsDiscovered = new HashSet<>();
        for (Post post : posts){
            int plantId = post.getPlant_id();
            plantsDiscovered.add(plantId);
        }
        return plantsDiscovered;
    }

    // get the newest one post of given uid
    public static Post getUserNewestPost(int uid){
        List<Post> allUserPosts = getPostsByUserId(uid);
        if (allUserPosts.isEmpty()) {
            return null;
        }
        Post newestPost = allUserPosts.get(0);
        for (Post post : allUserPosts) {
            if (post.getTimestamp().compareTo(newestPost.getTimestamp()) > 0) {
                newestPost = post;
            }
        }
        return newestPost;
    }

    // 生成十个min-max的不同的随机整数
    public static List<Integer> generateUniqueRandomNumbers(int count, int min, int max) {
        if (count <= 0 || min >= max || count > max - min + 1) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        List<Integer> randomNumbers = new ArrayList<>();
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();

        while (uniqueNumbers.size() < count) {
            int randomNumber = random.nextInt(max - min + 1) + min; // Generate random number in the range [min, max]
            uniqueNumbers.add(randomNumber);
        }

        randomNumbers.addAll(uniqueNumbers);

        return randomNumbers;
    }
    // 用于处理时间戳
    public static String formatTimestamp(String timestamp) {
        LocalDateTime dateTime = LocalDateTime.parse(timestamp);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter;

        if (dateTime.getYear() == currentDate.getYear()) {
            if (dateTime.toLocalDate().equals(currentDate)) {
                // 今天的日期，格式为 "Today HH:mm"
                formatter = DateTimeFormatter.ofPattern("'Today' HH:mm");
            } else {
                // 今年的其他日子，格式为 "MM-dd HH"
                formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            }
        } else {
            // 不是今年，格式为 "yyyy-MM-dd"
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        return dateTime.format(formatter);
    }

}