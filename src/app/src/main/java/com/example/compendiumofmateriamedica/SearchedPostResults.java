package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import model.Plant;
import model.RBTreeNode;

public class SearchedPostResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_post_results);
        this.getSupportActionBar().hide();
        ArrayList<Integer> dataToShow = (ArrayList<Integer>) getIntent().getExtras().getSerializable("post");
        if (dataToShow.size() != 0) {
            Log.println(Log.ASSERT, "DEBUG", "[SearchedPostResults] sample search result[0]: " + dataToShow);
        }

        // 上半部分显示搜索到的植物，下班部分显示植物相关的post


    }
}