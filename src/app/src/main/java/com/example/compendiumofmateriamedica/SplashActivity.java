package com.example.compendiumofmateriamedica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;

import java.io.IOException;

import model.Datastructure.DataType;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTree;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;
import model.GeneratorFactory;

public class SplashActivity extends AppCompatActivity {
    // RBTrees to save data
    private RBTree<User> userTree;
    private RBTree<Plant> plantTree;
    private RBTree<Post> postTree;

    // TreeManager based on Singleton Pattern, global access throughout the app
    private UserTreeManager userTreeManager;
    private PostTreeManager postTreeManager;
    private PlantTreeManager plantTreeManager;

    // Set the delay time in milliseconds
    private static final long SPLASH_DELAY = 3000; // 3 seconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // load data from files to trees
        try {
            userTree = (RBTree<User>) GeneratorFactory.tree(this, DataType.USER, R.raw.users);
            plantTree = (RBTree<Plant>) GeneratorFactory.tree(this, DataType.PLANT, R.raw.plants);
            postTree = (RBTree<Post>) GeneratorFactory.tree(this, DataType.POST, R.raw.posts);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // initialization of singleton instances before any other activities started
        userTreeManager = UserTreeManager.getInstance(userTree);
        postTreeManager = PostTreeManager.getInstance(postTree);
        plantTreeManager = PlantTreeManager.getInstance(plantTree);

        // check permission 'POST_NOTIFICATIONS'
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // request permission 'POST_NOTIFICATIONS' if do not have permission
            Toast.makeText(this, "Please grant notification permission in settings", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
        }

        // Delay transition to the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // start main activity
                startLoginActivity();
            }
        }, SPLASH_DELAY);

    }

    private void startLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        // Finish SplashActivity
        finish();
    }
}