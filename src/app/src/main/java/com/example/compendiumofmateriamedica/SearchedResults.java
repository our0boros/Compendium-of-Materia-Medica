package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.Adapters.GridAdapter;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
import model.Adapters.RowAdapter;


/**
 * @author: Hongjun Xu
 * @uid: u7733037
 * @datetime: 2024/05/16
 * @description: When the search syntax or simple search
 * obtains the specific plant ID and post ID, read the
 * list of these IDs and display them on the UI through
 * the adapter
 */
public class SearchedResults extends AppCompatActivity {
    ArrayList<Integer> dataToShow;
    boolean isPost;
    private TextView viewmore_1;
    private TextView viewmore_2;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_results);
        // load data from previous page
        dataToShow = (ArrayList<Integer>) getIntent().getExtras().getSerializable("idList");
        isPost = (boolean) getIntent().getExtras().getSerializable("isPost");
        if (dataToShow.size() != 0) {
            Log.println(Log.ASSERT, "DEBUG", "[SearchedPostResults] sample search result: " + dataToShow.size()
                    + " " + (isPost ? "Post" : "Plant"));
        }
        // Preprocess data
        ArrayList<Integer> plantIDs, postIDs;
        if (!isPost) {
            plantIDs = dataToShow;
            postIDs = new ArrayList<>();
            for (Integer id : plantIDs) {
                ArrayList<Post> temp = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.PLANT_ID, String.valueOf(id));
                for (Post post : temp) {
                    postIDs.add(post.getPost_id());
                }
            }
        } else {
            postIDs = dataToShow;
            Set<Integer> plantSets = new HashSet<>();
            for (Integer id : postIDs) {
                ArrayList<Post> temp = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(id));
                if (temp.size() != 0) plantSets.add(temp.get(0).getPlant_id());
            }
            plantIDs = new ArrayList<>();
            plantIDs.addAll(plantSets);
        }
        // ================================================ =============================
        // The upper part displays the searched plants, and the off-duty part displays plant-related posts.
        // ================================================ =============================

        // Display of plant related results
        RecyclerView plantResults = findViewById(R.id.plantLists);
        plantResults.setLayoutManager(new GridLayoutManager(this, 3));

        GridAdapter gridAdapter;
        try {
            gridAdapter = new GridAdapter(this, getFirstNItems(plantIDs, 6));
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        plantResults.setAdapter(gridAdapter);
        // ============================================================================
        RecyclerView postResults = findViewById(R.id.postLists);
        postResults.setLayoutManager(new GridLayoutManager(this, 1));

        RowAdapter rowAdapter;
        try {
            rowAdapter = new RowAdapter(this, getFirstNItems(postIDs, 2));
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        postResults.setAdapter(rowAdapter);

        // ============================================================================
        // VIEW MORE
        viewmore_1 = findViewById(R.id.searchedResultViewmore);
        viewmore_2 = findViewById(R.id.searchedResultViewmore2);

        viewmore_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridAdapter.setData(plantIDs);
            }
        });
        viewmore_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowAdapter.setData(postIDs);
            }
        });


        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    // Get the first n items of ArrayList, if there are less than n items, return the original list
    public static <T> ArrayList<T> getFirstNItems(ArrayList<T> list, int count) {
        ArrayList<T> resultList = new ArrayList<>();
        int size = Math.min(list.size(), count); // Get the smaller value between the size of the list and n
        for (int i = 0; i < size; i++) {
            resultList.add(list.get(i)); // Add the first n items to the result list
        }
        return resultList;
    }
}