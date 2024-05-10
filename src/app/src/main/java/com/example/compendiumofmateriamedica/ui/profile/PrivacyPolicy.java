package com.example.compendiumofmateriamedica.ui.profile;

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
public class PrivacyPolicy extends AppCompatActivity {

    private ImageView back;
    private TextView page_name;
    private TextView page_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_content);

        page_name=findViewById(R.id.page_name);
        page_name.setText("Privacy Policy");

        page_content=findViewById(R.id.page_content);
        page_content.setText("We will protect your privacy. Trust us!");

        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}