package com.example.compendiumofmateriamedica;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.compendiumofmateriamedica.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.IOException;

import model.DataType;
import model.GeneratorFactory;
import model.Plant;
import model.Post;
import model.RBTree;
import model.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    public RBTree<User> userTree;
    public RBTree<Plant> plantTree;
    public RBTree<Post> postTree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 运行加载数据的函数
        try {
            DataInitial();
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_camera, R.id.navigation_notifications)
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
}