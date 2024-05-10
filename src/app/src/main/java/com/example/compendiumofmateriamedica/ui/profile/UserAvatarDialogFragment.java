package com.example.compendiumofmateriamedica.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import model.Datastructure.User;
/**
 * @author: Tianhao Shan
 * @datetime: 2024/5
 * @description:
 */
public class UserAvatarDialogFragment extends BottomSheetDialogFragment {

    private ImageView image_avatar;
    private EditText image_url;
    private Button button_upload_url;
    private User currentUser;

    public UserAvatarDialogFragment() {
        // Required empty public constructor
    }

    // Create a new instance of the bottom sheet dialog fragment
    public static UserAvatarDialogFragment newInstance() {
        return new UserAvatarDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet_upload_avatar, container, false);
//        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");

        image_avatar = view.findViewById(R.id.image_avatar);
        image_url = view.findViewById(R.id.input_text);
        button_upload_url = view.findViewById(R.id.button_upload);

        button_upload_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the image URL entered by the user
                String imageUrl = image_url.getText().toString().trim();

                // TODO: Validate the URL

                //Perform upload operation using the URL (update user avatar_url)
                // TODO: update to current user and data file
                MainActivity.loadImageFromURL(getContext(), imageUrl, image_avatar, "Avatar");
//                currentUser.setAvatar_url(imageUrl);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release any resources or references to views
        image_avatar = null;
        image_url=null;
        button_upload_url=null;
    }
}


