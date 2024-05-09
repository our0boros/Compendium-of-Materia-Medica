package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SearchedResults extends AppCompatActivity {
    ArrayList<Integer> dataToShow;
    boolean isPost;
    private TextView viewmore_1;
    private TextView viewmore_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_results);
        this.getSupportActionBar().hide();
        dataToShow = (ArrayList<Integer>) getIntent().getExtras().getSerializable("idList");
        isPost = (boolean) getIntent().getExtras().getSerializable("isPost");
        if (dataToShow.size() != 0) {
            Log.println(Log.ASSERT, "DEBUG", "[SearchedPostResults] sample search result: " + dataToShow.size()
                    + " " + (isPost ? "Post" : "Plant"));
        }


        ArrayList<Integer> plantIDs, postIDs;
        if (!isPost) {
            plantIDs = dataToShow;
            postIDs = new ArrayList<>();
            for (Integer id : plantIDs) {
                ArrayList<RBTreeNode<Post>> temp = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.PLANT_ID, String.valueOf(id));
                for (RBTreeNode<Post> node : temp) {
                    postIDs.add(node.getValue().getPost_id());
                }
            }
        } else {
            postIDs = dataToShow;
            Set<Integer> plantSets = new HashSet<>();
            for (Integer id : postIDs) {
                ArrayList<RBTreeNode<Post>> temp = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(id));
                if (temp.size() != 0) plantSets.add(temp.get(0).getValue().getPlant_id());
            }
            plantIDs = new ArrayList<>();
            plantIDs.addAll(plantSets);
        }
        // =============================================================================
        // 上半部分显示搜索到的植物，下班部分显示植物相关的post
        // =============================================================================

        // 植物相关结果显示
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
    }

    // 获取 ArrayList 的前 n 项，如果数量不足 n 项则返回原始列表
    public static <T> ArrayList<T> getFirstNItems(ArrayList<T> list, int count) {
        ArrayList<T> resultList = new ArrayList<>();
        int size = Math.min(list.size(), count); // 获取列表的大小和 n 之间的较小值
        for (int i = 0; i < size; i++) {
            resultList.add(list.get(i)); // 将前 n 项添加到结果列表中
        }
        return resultList;
    }
}