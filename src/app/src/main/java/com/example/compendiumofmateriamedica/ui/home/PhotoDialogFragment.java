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
import com.github.chrisbanes.photoview.PhotoView;

/**
 * @author: Xing Chen
 * @datetime: 2024/5/4
 * @description: 这个类用于点击图片后显示图片的详细内容
 * 再次点击图片会关闭
 * 可以复用
 * 用法如下：
 * // 设置头像点击事件
 *             userAvatar.setOnClickListener(v -> {
 *                 PhotoDialogFragment avatarDialogFragment = PhotoDialogFragment.newInstance(postUserAvatarURL);
 *                 avatarDialogFragment.show(fragmentManager, "avatar_dialog");
 *             });
 */
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
        PhotoView photoView = view.findViewById(R.id.image_fragment_dialog);
        String photoUrl = getArguments().getString(PHOTO_URL);
        // load image
        Glide.with(this).load(photoUrl).error(R.drawable.load_image_fail).into(photoView);
        // Set the ImageView click listener to dismiss the dialog
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dismiss();
            }
        });
        return view;
    }
}
