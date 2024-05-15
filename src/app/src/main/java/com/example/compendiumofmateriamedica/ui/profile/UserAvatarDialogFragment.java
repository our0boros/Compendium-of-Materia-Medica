package com.example.compendiumofmateriamedica.ui.profile;

import static model.UtilsApp.isValidRoboHashURL;
import static model.UtilsApp.loadImageFromURL;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.compendiumofmateriamedica.LoginActivity;
import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import model.Datastructure.User;
import model.Datastructure.UserTreeManager;


public class UserAvatarDialogFragment extends BottomSheetDialogFragment {

    private ImageView image_avatar;
    private EditText image_url;
    private Button button_upload_url;
    private User currentUser;

    public UserAvatarDialogFragment() {
        // Required empty public constructor
    }

    // Create a new instance of the bottom sheet dialog fragment
    public static UserAvatarDialogFragment newInstance(User currentUser) {
        UserAvatarDialogFragment fragment = new UserAvatarDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("CurrentUser", currentUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentUser = (User) bundle.getSerializable("CurrentUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet_upload_avatar, container, false);

        image_avatar = view.findViewById(R.id.image_avatar);
        image_url = view.findViewById(R.id.input_text);
        button_upload_url = view.findViewById(R.id.button_upload);

        button_upload_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the image URL entered by the user
                String imageUrl = image_url.getText().toString().trim();
                // Check URL validation
                if (isValidRoboHashURL(imageUrl)){
                    //Perform upload operation using the URL (update user avatar_url)
                    loadImageFromURL(getContext(), imageUrl, image_avatar, "Avatar");
                    UserTreeManager.getInstance().findUserById(currentUser.getUser_id()).setAvatar_url(imageUrl);
                }else{
                    Toast.makeText(getContext(), "Invalid URL. We only accept avatar from 'https://robohash.org/'", Toast.LENGTH_SHORT).show();

                }
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

    // Interface to communicate dismissal event to activity
    public interface DialogDismissListener {
        void onDialogDismiss(User currentUser);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() instanceof DialogDismissListener) {
            // Get the current user from your dialog fragment
//            User currentUser = getCurrentUser(); // Implement this method to get the current user
            ((DialogDismissListener) getActivity()).onDialogDismiss(currentUser);
        }
    }


}


