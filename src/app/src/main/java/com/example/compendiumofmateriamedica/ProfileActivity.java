package com.example.compendiumofmateriamedica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.ui.profile.Messages;
import com.example.compendiumofmateriamedica.ui.profile.MyPoints;
import com.example.compendiumofmateriamedica.ui.profile.MyPost;
import com.example.compendiumofmateriamedica.ui.profile.MySearchHistory;
import com.example.compendiumofmateriamedica.ui.profile.PersonalInfo;
import com.example.compendiumofmateriamedica.ui.profile.Settings;

public class ProfileActivity extends AppCompatActivity {

    private TextView user_points, user_search, user_post,personal_information, messages, settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_points=findViewById(R.id.user_points);
        user_search=findViewById(R.id.user_search);
        user_post=findViewById(R.id.user_post);
        personal_information=findViewById(R.id.personal_information);
        messages=findViewById(R.id.messages);
        settings=findViewById(R.id.settings);

        // Set click listeners
        user_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MyPoints.class);
                startActivity(intent);
            }
        });
        user_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MySearchHistory.class);
                startActivity(intent);
            }
        });
        user_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MyPost.class);
                startActivity(intent);
            }
        });
        personal_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PersonalInfo.class);
                startActivity(intent);
            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Messages.class);
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Settings.class);
                startActivity(intent);
            }
        });


    }

}