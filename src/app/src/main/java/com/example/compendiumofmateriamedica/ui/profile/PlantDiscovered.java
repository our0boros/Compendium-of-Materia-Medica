package com.example.compendiumofmateriamedica.ui.profile;

import static com.example.compendiumofmateriamedica.SearchedResults.getFirstNItems;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Adapters.GridAdapter;
import model.Datastructure.PostTreeManager;
import model.Datastructure.User;
/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description: Show all plants user has posted
 */
public class PlantDiscovered extends AppCompatActivity {
    private User currentUser;
    private TextView pageName;
    private ImageView backButton;
    private RecyclerView plantsRecyclerView;
    private GridAdapter gridAdapter;
    private ArrayList<Integer> plantIdList;
    private final int N = 6; // Load 6 items at a time
    private int currentLoadedItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // Initialize views
        pageName = findViewById(R.id.page_name);
        pageName.setText("Discovered Plants");
        backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Get current user
        currentUser = (User) getIntent().getSerializableExtra("CurrentUser");

        // Get plant IDs discovered by the user
        Set<Integer> plantIdSet = PostTreeManager.getInstance().getUserPlantDiscovered(currentUser.getUser_id());

        // Initialize RecyclerView
        plantsRecyclerView = findViewById(R.id.messages_recyclerView);
        plantsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Load initial data
        plantIdList = new ArrayList<>(plantIdSet);
        ArrayList<Integer> initialData = getFirstNItems(plantIdList, N);
        currentLoadedItems = initialData.size();

        try {
            gridAdapter = new GridAdapter(this, initialData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        plantsRecyclerView.setAdapter(gridAdapter);

        // Setup scroll listener
        setupScrollListener();
    }

    // Method to setup scroll listener for RecyclerView
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

    // Method to load more items when scrolled to the bottom
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
