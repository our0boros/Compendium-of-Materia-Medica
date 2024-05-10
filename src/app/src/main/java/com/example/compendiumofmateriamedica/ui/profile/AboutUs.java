package com.example.compendiumofmateriamedica.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.R;
/**
 * @author: Tianhao Shan
 * @datetime: 2024/5
 * @description:
 */
public class AboutUs extends AppCompatActivity {

    private ImageView back;
    private TextView page_name;
    private TextView page_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_content);

        page_name=findViewById(R.id.page_name);
        page_name.setText("About Us");

        page_content=findViewById(R.id.page_content);
        page_content.setText("We are the team with peace and love.");

        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}