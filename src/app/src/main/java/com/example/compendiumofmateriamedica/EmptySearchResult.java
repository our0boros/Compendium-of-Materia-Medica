package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.compendiumofmateriamedica.R;

public class EmptySearchResult extends AppCompatActivity {

    TextView textView;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_search_result);

        textView = findViewById(R.id.noResults);
        // set empty result prompt
        textView.setText(getResources().getString(R.string.no_result));

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

}