
package com.example.compendiumofmateriamedica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.compendiumofmateriamedica.databinding.ActivityMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import model.Datastructure.NewEventHandler;
import model.Datastructure.User;

public class MainActivity extends AppCompatActivity implements NewEventHandler.EventObserver{

    private ActivityMainBinding binding;
    private NewEventHandler eventHandler;
    private TextView profileNotificationCountTextView;

    // Declare BadgeDrawable for the profile menu item
    private BadgeDrawable profileBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize UI elements
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Initialize profile badge
        initProfileBadge(navView);

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
//        // Unregister EventBus
////        EventBus.getDefault().unregister(this);
//        // Stop notification service
//        stopNotificationService();
    }

    // Method to initialize the badge for the profile menu item
    private void initProfileBadge(BottomNavigationView navView) {
        // Get the profile menu item view
        Menu menu = navView.getMenu();
        MenuItem profileMenuItem = menu.findItem(R.id.navigation_profile);

        // Initialize BadgeDrawable for the profile menu item
        profileBadge = navView.getOrCreateBadge(profileMenuItem.getItemId());
        profileBadge.setVisible(false); // Initially hide the badge
    }

    // Update Notification count (the number of unread messages) for the profile badge
    private void updateProfileNotificationCount() {
        int unreadCount = eventHandler.getUnreadNotifications();
        Log.d("MainActivity", "Unread Noti :" + unreadCount);
        Log.d("MainActivity", "Event List size :" + eventHandler.getEventList().size());
        if (unreadCount > 0) {
            profileBadge.setNumber(unreadCount); // Set the badge number
            profileBadge.setVisible(true); // Show the badge
        } else {
            profileBadge.setVisible(false); // Hide the badge if there are no unread notifications
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