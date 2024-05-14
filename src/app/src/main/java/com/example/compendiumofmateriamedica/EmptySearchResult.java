package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.compendiumofmateriamedica.R;

public class EmptySearchResult extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_search_result);

//        this.getSupportActionBar().hide();
        textView = findViewById(R.id.noResults);
        // 好小子
        textView.setText(getResources().getString(R.string.no_result));

    }
}