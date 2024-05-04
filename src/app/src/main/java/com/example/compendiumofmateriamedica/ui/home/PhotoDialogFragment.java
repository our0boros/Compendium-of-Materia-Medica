package com.example.compendiumofmateriamedica.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.compendiumofmateriamedica.R;

public class PhotoDialogFragment extends DialogFragment {

    private static final String PHOTO_URL = "photo_url";

    public static PhotoDialogFragment newInstance(String photoUrl) {
        PhotoDialogFragment fragment = new PhotoDialogFragment();
        Bundle args = new Bundle();
        args.putString(PHOTO_URL, photoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_dialog, container, false);
        ImageView imageView = view.findViewById(R.id.image_fragment_dialog);
        String photoUrl = getArguments().getString(PHOTO_URL);
        Glide.with(this).load(photoUrl).error(R.drawable.load_image_fail).into(imageView);
        return view;
    }
}
