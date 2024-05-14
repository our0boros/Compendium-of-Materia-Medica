
package com.example.compendiumofmateriamedica;

import static model.UtilsApp.dpToPx;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
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

import model.UtilsApp;

public class MainActivity extends AppCompatActivity implements NewEventHandler.EventObserver{

    private ActivityMainBinding binding;
    private NewEventHandler eventHandler;
    private TextView profileNotificationCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize UI elements
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Display the number of notifications at profile
        displayProfileNotification(navView);

        // Initialize navigation controller for fragments
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // Bind navView with navController
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Navigate to Social fragment by default
        navController.navigate(R.id.navigation_social);

        // Activity listen
        eventHandler = NewEventHandler.getInstance();
        eventHandler.addObserver(this);
    }

    // Actions when start MainActivity
    @Override
    protected void onStart() {
        super.onStart();
        // Register EventBus
//        EventBus.getDefault().register(this);
        // Start notification service
        startNotificationService();
    }

    // Actions when stop MainActivity
    @Override
    protected void onStop() {
        super.onStop();
        // Unregister EventBus
//        EventBus.getDefault().unregister(this);
        // Stop notification service
        stopNotificationService();
    }

    private void displayProfileNotification(BottomNavigationView navView){
        Context context = this;
        // Initialize TextView for notification
        profileNotificationCountTextView = new TextView(this);
        profileNotificationCountTextView.setBackground(ContextCompat.getDrawable(this, R.drawable.notification_count_background));
        profileNotificationCountTextView.setTextColor(Color.WHITE);
        profileNotificationCountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        profileNotificationCountTextView.setGravity(Gravity.CENTER);
        profileNotificationCountTextView.setPadding(dpToPx(this,4), dpToPx(this,1), dpToPx(this,4), dpToPx(this,1));
        profileNotificationCountTextView.setVisibility(View.GONE);

        // Add TextView to 'profile' at navigation bar
        @SuppressLint("RestrictedApi") BottomNavigationMenuView menuView = (BottomNavigationMenuView) navView.getChildAt(0);
        @SuppressLint("RestrictedApi") BottomNavigationItemView profileView = (BottomNavigationItemView) menuView.getChildAt(2);  // 'profile' is at index 2 of menu
        // Create a new FrameLayout to contain TextView
        FrameLayout notificationContainer = new FrameLayout(this);
        FrameLayout.LayoutParams containerParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        containerParams.setMargins(60, 16, 0, 0);  // Modify the position of TextView relative to 'profile' icon in navigation bar
        notificationContainer.setLayoutParams(containerParams);
        // Add TextView to the new FrameLayout
        notificationContainer.addView(profileNotificationCountTextView);
        // Add the new FrameLayout to profileView
        profileView.addView(notificationContainer);

    }

    // Update Notification count (the number of unread messages)
    private void updateProfileNotificationCount() {
        int unreadCount = eventHandler.getUnreadNotifications();
        Log.d("MainActivity", "Unread Noti :" + unreadCount);
        Log.d("MainActivity", "Event List size :" + eventHandler.getEventList().size());
        if (unreadCount > 0) {
            profileNotificationCountTextView.setText(String.valueOf(unreadCount));
            profileNotificationCountTextView.setVisibility(View.VISIBLE);
        } else {
            profileNotificationCountTextView.setVisibility(View.GONE);
        }
    }

    private void startNotificationService() {
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("User", getIntent().getSerializableExtra("User"));
        startService(intent);
    }

    private void stopNotificationService() {
        Intent intent = new Intent(this, NotificationService.class);
        stopService(intent);
    }

    //  Activity receives a new intent while it's already running and navigates to a specified fragment
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // Check new intent (navigate to other fragment)
        if (intent.hasExtra("navigate_fragment_id")) {
            int fragmentId = intent.getIntExtra("navigate_fragment_id", 0);
            // Navigate to social fragment by default
            if (fragmentId != 0) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
                navController.navigate(fragmentId);
            }
        }
        // 返回Social页面后，刷新页面以显示用户刚刚发布的Post
        // TODO
    }

    public User getCurrentUser(){
        return (User) getIntent().getSerializableExtra("User");
    }

    @Override
    public void onEventChanged() {
        updateProfileNotificationCount();
    }



}