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

/**
 * @author: Hongjun Xu, Xing Chen
 * @datetime: 2024/5/2
 * @description: A fragment to show the social page in app
 * using HomeViewModel to control the datastream.
 */
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
        // ser adapter
        postAdapter = new PostAdapter(getContext(), new ArrayList<>());
        postsRecyclerView.setAdapter(postAdapter);
        // set layout of recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        postsRecyclerView.setLayoutManager(layoutManager);


        // observe changes of the posts
        homeViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            postAdapter.setPosts(posts);
//            postAdapter.notifyDataSetChanged();
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