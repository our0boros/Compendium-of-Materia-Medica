package com.example.compendiumofmateriamedica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.Manifest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * @author: Haochen Gong
 * @description: 暂定如果要分享post，显示的编辑页面（已完成gps获取并显示的后端部分）。
 **/
public class PostShare extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_share);

        // 实现gps获取和显示
        locationText = (TextView) findViewById(R.id.locationText);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(PostShare.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (!addressList.isEmpty()) {
                            Address address = addressList.get(0);
                            String addressText = address.getAddressLine(0); // 获取完整地址
                            locationText.setText(location.getLatitude() + ", " + location.getLongitude() + "\nAddress：" + addressText);
                        } else {
                            locationText.setText(location.getLatitude() + ", " + location.getLongitude() + "\nAddress: " + "No address found");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Log.e("Geocoder", "Service Not Available", e);
                        locationText.setText(location.getLatitude() + ", " + location.getLongitude() + "\nError: Unable to get address");
                    }
                }
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
        }

        locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

    }


}