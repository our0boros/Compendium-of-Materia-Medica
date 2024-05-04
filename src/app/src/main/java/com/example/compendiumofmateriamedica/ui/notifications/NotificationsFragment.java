package com.example.compendiumofmateriamedica.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.databinding.FragmentNotificationsBinding;
import com.example.compendiumofmateriamedica.ui.home.PhotoDialogFragment;

import model.User;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private User currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");

        // user avatar
        ImageView userAvatar = binding.profileUserAvatar;
        MainActivity.loadImageFromURL(getContext(), currentUser.getAvatar(), userAvatar, "Avatar");
        // click on avatar will show big picture
        userAvatar.setOnClickListener(v -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(currentUser.getAvatar());
            photoDialogFragment.show(getParentFragmentManager(), "avatar");
        });
        // username
        TextView textView = binding.profileUsername;
        // user posts number
        TextView postsNumber = binding.profilePostsNumber;
        // text 'Posts'
        TextView postsText = binding.profilePostsText;
        // set username
        notificationsViewModel.updateUsername(currentUser.getName());
        notificationsViewModel.getUsername().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}