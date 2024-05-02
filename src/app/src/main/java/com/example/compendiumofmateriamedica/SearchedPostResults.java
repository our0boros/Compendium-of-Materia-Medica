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

        ArrayList<RBTreeNode<Plant>> dataToShow = (ArrayList<RBTreeNode<Plant>>) getIntent().getExtras().getSerializable("post");
        if (dataToShow.size() != 0) {
            Log.println(Log.ASSERT, "DEBUG", dataToShow.get(0).getKey() + "");
        }

    }
}