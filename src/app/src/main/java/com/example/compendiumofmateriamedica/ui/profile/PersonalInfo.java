package com.example.compendiumofmateriamedica.ui.profile;



import static model.UtilsApp.loadImageFromURL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;

import model.Datastructure.User;

/**
 * @author: Tianhao Shan
 * @datetime: 2024/5
 * @description:
 */
public class PersonalInfo extends AppCompatActivity implements UserAvatarDialogFragment.DialogDismissListener {

    private ImageView back;
    private ImageView avatar;
    private TextView user_name;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_personal_info);
        currentUser = (User) getIntent().getSerializableExtra("CurrentUser");

        // display user avatar and name
        user_name = findViewById(R.id.profile_username);
        user_name.setText(currentUser.getUsername());
        avatar = findViewById(R.id.user_avatar);
        loadImageFromURL(this, currentUser.getAvatar_url(), avatar, "Avatar");


        // jump to dialog fragment
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAvatarDialogFragment dialogFragment = UserAvatarDialogFragment.newInstance(currentUser);
                dialogFragment.show(getSupportFragmentManager(), "user_avatar_dialog");
            }
        });
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserNameDialogFragment dialogFragment = UserNameDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "user_name_dialog");
            }

        });


        // back to Profile fragment page
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
    public void onDialogDismiss(User currentUser) {
        this.currentUser=currentUser;
        // Update user info displayed
        user_name = findViewById(R.id.profile_username);
        user_name.setText(currentUser.getUsername());
        avatar = findViewById(R.id.user_avatar);
        loadImageFromURL(this, currentUser.getAvatar_url(), avatar, "Avatar");
    }

}