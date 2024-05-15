package com.example.compendiumofmateriamedica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;

import java.io.IOException;

import model.Datastructure.DataType;
import model.Datastructure.GeneratorFactory;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTree;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;


public class SplashActivity extends AppCompatActivity {
    // RBTrees to save data
    private RBTree<User> userTree;
    private RBTree<Plant> plantTree;
    private RBTree<Post> postTree;

    // TreeManager based on Singleton Pattern, global access throughout the app
    private UserTreeManager userTreeManager;
    private PostTreeManager postTreeManager;
    private PlantTreeManager plantTreeManager;
    private GeneralFunctions generalFunctions;

    // Set the delay time in milliseconds
    private static final long SPLASH_DELAY = 3000; // 3 seconds
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // check permission 'POST_NOTIFICATIONS'
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // request permission 'POST_NOTIFICATIONS' if do not have permission
            Toast.makeText(this, "Please grant notification permission in settings", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
        } else {
            // If permission is already granted, proceed with initialization
            proceedWithInitialization();
        }
    }
    private void proceedWithInitialization(){
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            // If permission is granted, proceed with initialization
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                proceedWithInitialization();
            } else {
                // If permission is denied, show a message and proceed without notification permission
                Toast.makeText(this, "Notification permission denied. Some features may not work properly.", Toast.LENGTH_SHORT).show();
                proceedWithInitialization();
            }
        }
    }
}