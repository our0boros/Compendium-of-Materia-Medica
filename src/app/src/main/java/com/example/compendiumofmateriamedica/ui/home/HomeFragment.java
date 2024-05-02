package com.example.compendiumofmateriamedica.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import model.PostAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // configure RecyclerView
        postsRecyclerView = binding.postsRecyclerView;
        postAdapter = new PostAdapter(new ArrayList<>());
        postsRecyclerView.setAdapter(postAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        postsRecyclerView.setLayoutManager(layoutManager);


        // observe changes
        homeViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            postAdapter.setPosts(posts);
            postAdapter.notifyDataSetChanged();
        });


        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}