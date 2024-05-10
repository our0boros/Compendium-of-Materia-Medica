package com.example.compendiumofmateriamedica.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.R;

/**
 * @author: Hongjun Xu, Xing Chen
 * @datetime: 2024/5/2
 * @description: A fragment to show the social page in app
 * using HomeViewModel to control the datastream.
 */
public class MyPost extends AppCompatActivity {

    private TextView page_name;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        page_name=findViewById(R.id.page_name);
        page_name.setText("My Posts");
        back=findViewById(R.id.back_btn);
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