package com.example.compendiumofmateriamedica.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.compendiumofmateriamedica.R;

public class MySearchHistory extends AppCompatActivity {

    private TextView page_name;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_list);

        page_name=findViewById(R.id.page_name);
        page_name.setText("My Search History");
        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySearchHistory.this, ProfileFragment.class);
                startActivity(intent);
            }
        });


    }
}