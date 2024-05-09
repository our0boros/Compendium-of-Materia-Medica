package com.example.compendiumofmateriamedica.ui.profile;


import android.Manifest;

import static com.example.compendiumofmateriamedica.MainActivity.getPostsByUserId;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.databinding.FragmentProfileBinding;
import com.example.compendiumofmateriamedica.ui.social.PhotoDialogFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import model.Datastructure.NewEventHandler;
import model.Datastructure.User;


public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;
    private User currentUser;
    private NewEventHandler eventHandler;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
    LocationManager locationManager;
    LocationListener locationListener;
    private TextView user_location;
    private static final long NOTIFICATION_UPDATE_INTERVAL = 5000; // 通知状态5秒更新一次
    private TextView notificationCountTextView;
    private Handler handler;
    private Runnable notificationUpdateRunnable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = binding.getRoot();

        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");
        eventHandler = NewEventHandler.getInstance();

        // user avatar
        ImageView userAvatar = binding.profileUserAvatar;
        MainActivity.loadImageFromURL(getContext(), currentUser.getAvatar_url(), userAvatar, "Avatar");
        // click on avatar will show big picture
        userAvatar.setOnClickListener(v -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(currentUser.getAvatar_url());
            photoDialogFragment.show(getParentFragmentManager(), "avatar");
        });

        // user name
        TextView user_name=binding.profileUsername;
        mViewModel.updateUserName(currentUser.getUsername());
        mViewModel.getUserName().observe(getViewLifecycleOwner(), user_name::setText);

        // user location
        user_location = binding.userLocation;


        // user post number
        TextView user_post=binding.userPost;
//        Log.d("ProfileFragment", "Current user's uid is " + currentUser.getUser_id());
//        Log.d("ProfileFragment", "He has " + getPostsByUserId(currentUser.getUser_id()).size() + " posts.");
//        Log.d("ProfileFragment", "Trying to get a post using post id " + 94);
//        Log.d("ProfileFragment", "The post content is " + MainActivity.getPostByPostId(94).getContent());
        mViewModel.updateUserPost(getPostsByUserId(currentUser.getUser_id()));
        mViewModel.getUserPost().observe(getViewLifecycleOwner(), value -> {
            // Convert integer value to string and set it to TextView
            user_post.setText(String.valueOf(value));
        });
        // messages
        TextView messages=binding.messages;
        // 找到显示通知数量的 TextView
        notificationCountTextView = binding.messagesCount;
        // 创建 Handler 和 Runnable,用于定期更新通知数量
        handler = new Handler(Looper.getMainLooper());
        notificationUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                updateNotificationCount();
                handler.postDelayed(this, NOTIFICATION_UPDATE_INTERVAL);
            }
        };

        // other features in profile
        TextView personal_information=binding.personalInformation;
        TextView settings=binding.settings;

        // deleted profile features (maybe Temporary)
//        TextView user_points = binding.userPoints;
//        TextView user_search=binding.userSearch;

        // Initialize LocationManager
        locationManager = (LocationManager) ContextCompat.getSystemService(requireContext(), LocationManager.class);
        // Define a LocationListener

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Convert latitude and longitude to address
                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        String addressLine = addresses.get(0).getAddressLine(0);
                        // Set the address to TextView
                        user_location.setText(addressLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };
        // Request location updates
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Request location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }


        // jump logic
//        user_points.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MyPoints.class);
//                startActivity(intent);
//            }
//        });
//        user_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MySearchHistory.class);
//                startActivity(intent);
//            }
//        });
        user_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyPost.class);
                intent.putExtra("CurrentUser", currentUser); // Pass the current user object
                startActivity(intent);
            }
        });
        personal_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalInfo.class);
                intent.putExtra("CurrentUser", currentUser); // Pass the current user object
                startActivity(intent);
            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessagesActivity.class);
                intent.putExtra("CurrentUser", currentUser); // Pass the current user object
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Settings.class);
                intent.putExtra("CurrentUser", currentUser); // Pass the current user object
                startActivity(intent);
            }
        });

        return root;
    }
    // when fragment is resumed, require location update
    public void onResume(){
        super.onResume();
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        // 开始定期更新通知数量
        handler.post(notificationUpdateRunnable);
    }
    // when fragment is paused, close update
    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
        // 停止定期更新通知数量
        handler.removeCallbacks(notificationUpdateRunnable);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateNotificationCount() {
        // 获取当前用户的未读通知数量,并更新界面
        notificationCountTextView.setText(eventHandler.getEventList().size());
    }

}