package com.example.compendiumofmateriamedica.ui.profile;


import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.databinding.FragmentProfileBinding;
import com.example.compendiumofmateriamedica.ui.home.PhotoDialogFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import model.User;


public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;
    private User currentUser;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = binding.getRoot();

        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");

        // user avatar
        ImageView userAvatar = binding.userAvater;
        MainActivity.loadImageFromURL(getContext(), currentUser.getAvatar(), userAvatar, "Avatar");
        // click on avatar will show big picture
        userAvatar.setOnClickListener(v -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(currentUser.getAvatar());
            photoDialogFragment.show(getParentFragmentManager(), "avatar");
        });

        // user name
        TextView user_name=binding.userName;
        mViewModel.updateUserName(currentUser.getName());
        mViewModel.getUserName().observe(getViewLifecycleOwner(), user_name::setText);

        // user location
        TextView user_location=binding.userLocation;
        // Initialize LocationManager
        locationManager = (LocationManager) ContextCompat.getSystemService(requireContext(), LocationManager.class);
        // Define a LocationListener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Convert latitude and longitude to address
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
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

        // user post number
        TextView user_post=binding.userPost;
        mViewModel.updateUserPost(getPostsByUserId(currentUser.getId()));
        mViewModel.getUserPost().observe(getViewLifecycleOwner(), value -> {
            // Convert integer value to string and set it to TextView
            user_post.setText(String.valueOf(value));
        });

        // other features in profile
        TextView personal_information=binding.personalInformation;
        TextView messages=binding.messages;
        TextView settings=binding.settings;

        TextView user_points = binding.userPoints;
        TextView user_search=binding.userSearch;




        // jump logic
        user_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyPoints.class);
                startActivity(intent);
            }
        });
        user_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MySearchHistory.class);
                startActivity(intent);
            }
        });
        user_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyPost.class);

                startActivity(intent);
            }
        });
        personal_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalInfo.class);
                startActivity(intent);
            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Messages.class);

                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}