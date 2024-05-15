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
import model.Datastructure.UserTreeManager;

/**
 * @author: Tianhao Shan
 * @datetime: 2024/5
 * @description:
 */
public class UserNameDialogFragment extends BottomSheetDialogFragment {

    private ImageView image_avatar;
    private EditText username;
    private Button button_upload_username;
    private User currentUser;

    public UserNameDialogFragment() {
        // Required empty public constructor
    }

    // Create a new instance of the bottom sheet dialog fragment
    public static UserNameDialogFragment newInstance() {
        return new UserNameDialogFragment();
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
        View view = inflater.inflate(R.layout.bottom_sheet_upload_username, container, false);
//        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");


        username = view.findViewById(R.id.input_text);
        button_upload_username = view.findViewById(R.id.button_upload);

        button_upload_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username entered by the user
                String usern = username.getText().toString();

                // TODO: Validate username

                //Perform update username
                // TODO: update to current user and data file
                username.setText(usern);
//                currentUser.setUser_name(usern);
            }
        });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release any resources or references to views
        image_avatar = null;
        username=null;
        button_upload_username=null;
    }

}


