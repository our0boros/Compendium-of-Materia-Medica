package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import model.GridAdapter;

public class SearchedResults extends AppCompatActivity {
    ArrayList<Integer> dataToShow;
    boolean isPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_results);
        this.getSupportActionBar().hide();
        dataToShow = (ArrayList<Integer>) getIntent().getExtras().getSerializable("idList");
        isPost = (boolean) getIntent().getExtras().getSerializable("plantOrPost");
        if (dataToShow.size() != 0) {
            Log.println(Log.ASSERT, "DEBUG", "[SearchedPostResults] sample search result: " + dataToShow);
        }

        // =============================================================================
        // 上半部分显示搜索到的植物，下班部分显示植物相关的post
        // =============================================================================

        // 初始化 RecyclerView
        RecyclerView recyclerView = findViewById(R.id.plantLists);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        GridAdapter adapter = null;
        try {
            adapter = new GridAdapter(this, dataToShow);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        recyclerView.setAdapter(adapter);


    }
}