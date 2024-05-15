package com.example.compendiumofmateriamedica.ui.profile;


import static model.UtilsApp.loadImageFromURL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.ui.social.PhotoDialogFragment;

import model.Datastructure.PostTreeManager;
import model.Datastructure.User;

public class ProfilePage extends AppCompatActivity {
    private User appUser;
    private User profileUser;
    private ImageView userAvatar;
    private TextView username;
    private ImageView userLevel;
    private TextView plants_discovered_number;
    private TextView posts_number;
    private ImageView back;
    private final PostTreeManager postTreeManager = PostTreeManager.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // get user
        appUser = (User) getIntent().getSerializableExtra("AppUser");
        profileUser = (User) getIntent().getSerializableExtra("ProfileUser");
        String name = profileUser.getUsername();

        // user avtar
        userAvatar = findViewById(R.id.profile_page_avatar);
        loadImageFromURL(this, profileUser.getAvatar_url(),userAvatar,"Avatar");
        userAvatar.setOnClickListener(v -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(profileUser.getAvatar_url());
            photoDialogFragment.show(getSupportFragmentManager(), "photo_dialog");
        });
        // username
        username = findViewById(R.id.profile_page_username);
        username.setText(profileUser.getUsername());

        // user level image
        userLevel = findViewById(R.id.profile_page_level_icon);

        // plants discovered
        plants_discovered_number = findViewById(R.id.profile_page_plants_number);
        updatePlantNumber();
        plants_discovered_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this, PlantDiscovered.class);
                intent.putExtra("CurrentUser", profileUser); // Pass the current user object
                startActivity(intent);
            }
        });

        // post number
        posts_number = findViewById(R.id.profile_page_posts_number);
        updatePostsNumber();
        posts_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this, MyPost.class);
                intent.putExtra("AppUser", appUser);
                intent.putExtra("CurrentUser", profileUser); // Pass the current user object
                startActivity(intent);
            }
        });

        // back button
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            // when start an activity from a fragment, the fragment never got killed
            // simply come back to original fragment by kill the current activity
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        updatePostsNumber();
        updatePlantNumber();
    }
    private void updatePostsNumber() {
        int postCount = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.UID, profileUser.getUser_id()).size();
        Log.d("ProfilePage", "Current user " + profileUser.getUsername() + " has " + postCount + " posts in post tree manager.");
        posts_number.setText(String.valueOf(postCount));
    }
    private void updatePlantNumber(){
        int plantDiscovered = postTreeManager.getUserPlantDiscovered(profileUser.getUser_id()).size();
        plants_discovered_number.setText(String.valueOf(plantDiscovered));
        ProfileFragment.setUserLevelImage(userLevel, plantDiscovered);
    }

}
