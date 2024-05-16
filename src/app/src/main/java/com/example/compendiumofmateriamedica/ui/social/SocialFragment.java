package com.example.compendiumofmateriamedica.ui.social;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.databinding.FragmentSocialBinding;

import java.util.ArrayList;

import model.Adapters.PostAdapter;
import model.Datastructure.User;

/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description: A fragment to show user's posts
 */
public class SocialFragment extends Fragment {

    private FragmentSocialBinding binding;
    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;
    private User currentUser;
    private SocialViewModel socialViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Instantiate the SocialViewModel
        socialViewModel = new ViewModelProvider(this).get(SocialViewModel.class);
        binding = FragmentSocialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Get the current user from the intent
        currentUser = (User) requireActivity().getIntent().getSerializableExtra("User");

        // Configure RecyclerView and PostAdapter
        postsRecyclerView = binding.postsRecyclerView;
        postAdapter = new PostAdapter(getContext(), new ArrayList<>(), getParentFragmentManager(), true, currentUser);
        postsRecyclerView.setAdapter(postAdapter);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Observe changes in postsLiveData and update the adapter accordingly
        socialViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> postAdapter.setPosts(posts));

        // Add a scroll listener to load more posts when scrolled to the bottom
        postsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    // Load more posts when scrolled to the bottom
                    socialViewModel.loadMorePosts(5);
                    // Show a toast message indicating that more posts have been loaded
                    Toast.makeText(getContext(), "Loaded more posts", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding
        binding = null;
        postAdapter.releaseSoundPool();
    }
}
