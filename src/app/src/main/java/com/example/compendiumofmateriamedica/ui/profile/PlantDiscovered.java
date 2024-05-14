package com.example.compendiumofmateriamedica.ui.profile;

import static com.example.compendiumofmateriamedica.SearchedResults.getFirstNItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.compendiumofmateriamedica.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Adapters.GridAdapter;
import model.Adapters.RowAdapter;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
import model.Datastructure.User;
/**
 * @author: Xing Chen
 * @datetime: 2024/5/10
 * @description: show the plants user discovered in a grid
 */
public class PlantDiscovered extends AppCompatActivity {
    private User currentUser;
    private TextView page_name;
    private ImageView back;

    private RecyclerView plantsRecyclerView;
    private GridAdapter gridAdapter;
    private ArrayList<Integer> plantIdList;

    private final int N = 6;  // 每次加载10个条目
    private int currentLoadedItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // 当前user
        currentUser = (User) this.getIntent().getSerializableExtra("CurrentUser");

        page_name=findViewById(R.id.page_name);
        page_name.setText("Discovered Plants");

        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            // when start an activity from a fragment, the fragment never got killed
            // simply come back to original fragment by kill the current activity
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        PostTreeManager postTreeManager=PostTreeManager.getInstance();
        // show PLANTS
        Set<Integer> plantIdSet = postTreeManager.getUserPlantDiscovered(currentUser.getUser_id());
//        for (Integer id : plantIdSets) {
//            ArrayList<RBTreeNode<Plant>> temp = PlantTreeManager.getInstance().search(PlantTreeManager.PlantInfoType.ID, String.valueOf(id));
//            if (temp.size() != 0) plantIdSets.add(temp.get(0).getValue().getPlant_id());
//        }
//        plantIDs = new ArrayList<>();
//        plantIDs.addAll(plantIdSets);



        plantsRecyclerView = findViewById(R.id.messages_recyclerView);
        plantsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        plantIdList = new ArrayList<>(plantIdSet);
        ArrayList<Integer> initialData = getFirstNItems(plantIdList, N);
        currentLoadedItems = initialData.size();

        try {
            gridAdapter = new GridAdapter(this, getFirstNItems(initialData,N));
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        plantsRecyclerView.setAdapter(gridAdapter);

        setupScrollListener();

    }
    private void setupScrollListener() {
        plantsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) { // 1 for down direction
                    loadMoreItems();
                }
            }
        });
    }

    private void loadMoreItems() {
        int totalItems = plantIdList.size();
        int itemsToLoad = Math.min(N, totalItems - currentLoadedItems);
        if (itemsToLoad > 0) {
            List<Integer> moreItems = plantIdList.subList(currentLoadedItems, currentLoadedItems + itemsToLoad);
            gridAdapter.addData(moreItems);
            currentLoadedItems += itemsToLoad;
        }
    }
}