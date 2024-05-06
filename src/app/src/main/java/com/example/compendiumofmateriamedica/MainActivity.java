package com.example.compendiumofmateriamedica;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.compendiumofmateriamedica.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import model.DataType;
import model.GeneratorFactory;
import model.Plant;
import model.Post;
import model.PostTreeManager;
import model.RBTree;
import model.RBTreeNode;
import model.User;
import model.UserTreeManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    public RBTree<User> userTree;
    public RBTree<Plant> plantTree;
    public RBTree<Post> postTree;
    public static UserTreeManager userTreeManager;
    public static PostTreeManager postTreeManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 运行加载数据的函数
        try {
            DataInitial();
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }

        // 初始化TreeManagers
        userTreeManager = new UserTreeManager(userTree);
        postTreeManager = new PostTreeManager(postTree);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_camera, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // 隐藏顶部那个活动栏目
        this.getSupportActionBar().hide();
    }


    /**
     * @author: Haochen Gong
     * @description: 加载数据
     **/
    private void DataInitial() throws JSONException, IOException {
        userTree = (RBTree<User>) GeneratorFactory.tree(this, DataType.USER, R.raw.users);
        plantTree = (RBTree<Plant>) GeneratorFactory.tree(this, DataType.PLANT, R.raw.plants);
        postTree = (RBTree<Post>) GeneratorFactory.tree(this, DataType.POST, R.raw.posts);
    }

    /**
     * 获取当前 Activity 下的Tree变量
     * @return
     */
    public RBTree<Plant> getPlantTree() {
        return plantTree;
    }

    public RBTree<Post> getPostTree() {
        return postTree;
    }

    public RBTree<User> getUserTree() {
        return userTree;
    }

    /**
     * 根据uid找用户的用户名和头像
     * 下面的方法暂时用于在PostAdapter中，在social界面显示用户名和头像
     */
    public static User findUserById(int uid){
        if (userTreeManager == null) {
            Log.w("UserTreeManager", "UserTreeManager未初始化");
            return null;
        }
        // 调用userTreeManager的搜索方法来找用户
        ArrayList<RBTreeNode<User>> users = userTreeManager.search(UserTreeManager.UserInfoType.ID, uid);

        // 因为UID都是唯一的，所以我们的users中应当只有一个user
        if (!users.isEmpty()) {
            User foundUser = users.get(0).getValue();
            Log.d("UserTreeManager", "找到用户: " + foundUser.getName());
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
             .placeholder(errorImage) // 加载中的占位图
             .error(errorImage) // 加载失败的错误图
             .into(imageView);
    }

    /**
     * 通过postID获取post
     * @param postId
     * @return
     */
    public static Post getPostByPostId(int postId) {
        ArrayList<RBTreeNode<Post>> searchResult = postTreeManager.search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(postId));
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

    // get all posts by user with uid
    public static List<Post> getPostsByUserId(int uid){
        ArrayList<RBTreeNode<Post>>  user_post_data = postTreeManager.search(PostTreeManager.PostInfoType.UID, String.valueOf(uid));
        List<Post> user_post_data_list= new ArrayList<>();
        if (!user_post_data.isEmpty()) {
            for (RBTreeNode<Post> node : user_post_data) {
                user_post_data_list.add(node.getValue());
            }
            return user_post_data_list;
        }
        return null;
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

}