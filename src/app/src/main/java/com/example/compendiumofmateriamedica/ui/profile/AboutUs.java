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
 * @uid: u7709429
 * @datetime: 2024/05/16
 * @description: Activity with 'About us' content, introduction of our group
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
        page_content.setText("Welcome to our team's About Us page!\n" +
                "\n" +
                "We are a dedicated group of five individuals brought together by our passion for technology and environmental conservation. As part of our coursework in COMP6445, we have embarked on an exciting journey to collaborate on a final project that aims to make a positive impact through mobile technology.\n" +
                "\n" +
                "Our mission is simple, yet profound: to develop a mobile Android app focused on plant search and sharing. By harnessing the power of mobile technology, we aim to empower users to explore and engage with the rich biodiversity around them, while fostering a deeper connection with nature.\n" +
                "\n" +
                "Meet our team members:\n" +
                "Haochen Gong\n" +
                "Xing Chen\n" +
                "Yusi Zhong\n" +
                "Hongjun Xu\n" +
                "Tianhao Shan\n" +
                "\n" +
                "Together, we bring a diverse range of skills, experiences and perspectives, united by our shared vision of using technology to protect the environment. Through our app, we aim to not only encourage more people to actively participate in protecting plants and preserving ecosystems, but also to educate and inspire a wider audience about the importance of environmental conservation.\n" +
                "\n" +
                "Join us on this journey as we strive to make a difference, one plant at a time.");

        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}