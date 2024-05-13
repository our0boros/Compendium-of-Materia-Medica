package com.example.compendiumofmateriamedica.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.ui.social.PhotoDialogFragment;

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
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // get user
        appUser = (User) getIntent().getSerializableExtra("AppUser");
        profileUser = (User) getIntent().getSerializableExtra("ProfileUser");

        // user avtar
        userAvatar = findViewById(R.id.profile_page_avatar);
        MainActivity.loadImageFromURL(this, profileUser.getAvatar_url(),userAvatar,"Avatar");
        userAvatar.setOnClickListener(v -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(profileUser.getAvatar_url());
            photoDialogFragment.show(getSupportFragmentManager(), "photo_dialog");
        });

        username = findViewById(R.id.profile_page_username);
        username.setText(profileUser.getUsername());


        userLevel = findViewById(R.id.profile_page_level_icon);
        plants_discovered_number = findViewById(R.id.profile_page_plants_number);
        posts_number = findViewById(R.id.profile_page_posts_number);

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
}
