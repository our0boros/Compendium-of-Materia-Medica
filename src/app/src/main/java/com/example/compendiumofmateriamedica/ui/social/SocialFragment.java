package com.example.compendiumofmateriamedica.ui.social;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
 * @author: Hongjun Xu, Xing Chen
 * @datetime: 2024/5/2
 * @description: A fragment to show the social page in app
 * using HomeViewModel to control the datastream.
 */
public class SocialFragment extends Fragment {

    private FragmentSocialBinding binding;

    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;
    private User currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SocialViewModel homeViewModel =
                new ViewModelProvider(this).get(SocialViewModel.class);

        binding = FragmentSocialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");
        // configure RecyclerView
        postsRecyclerView = binding.postsRecyclerView;
        // ser adapter
        postAdapter = new PostAdapter(getContext(), new ArrayList<>(), getParentFragmentManager(), true, currentUser);
        postsRecyclerView.setAdapter(postAdapter);
        // set layout of recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        postsRecyclerView.setLayoutManager(layoutManager);


        // observe changes of the posts
        homeViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            postAdapter.setPosts(posts);
//            postAdapter.notifyDataSetChanged();
        });

        // a scroll listener to show more posts when rolled at the bottom
        postsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 获取 RecyclerView 的布局管理器
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 获取当前可见的子项数量
                int visibleItemCount = layoutManager.getChildCount();
                // 获取总的子项数量
                int totalItemCount = layoutManager.getItemCount();
                // 获取第一个可见子项的位置
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                // 判断是否滚动到了底部
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0 ) {
                    // 如果滚动到了底部，调用 ViewModel 的 loadMorePosts 方法加载更多帖子

                    homeViewModel.loadMorePosts(5);
                    Log.d("HomeFragment", "Bottom of list reached, attempting to load more posts.");
                    Toast.makeText(getContext(), "Loaded more posts", Toast.LENGTH_SHORT).show();
                }
                //TODO 只有在拉到底后再向下拉起才会触发更新,
            }
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