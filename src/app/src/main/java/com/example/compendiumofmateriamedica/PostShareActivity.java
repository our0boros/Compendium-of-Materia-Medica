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
import android.widget.TextView;
import android.Manifest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import model.User;


/**
 * @author: Haochen Gong, Xing Chen
 * @description: 用于发布Post
 * 实现了GPS定位并在界面上显示当前位置的功能。
 *
 **/
public class PostShareActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationText;

    private User currentUser;

    //一个图片链接，用于测试发布post，实际的图片应当由调用这个Activity的
    private final String testImageURL = "https://picsum.photos/647/704";


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
                // 当位置发生变化时，使用Geocoder将经纬度转换为可读地址
                if (location != null) {
                    Geocoder geocoder = new Geocoder(PostShareActivity.this, Locale.getDefault());
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
                // 如果位置服务被禁用，引导用户到设置页面开启位置服务
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        // 如果运行设备为Android 6.0（API 23）以上，需要动态请求位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
        }

        // 请求位置更新，设置最小更新时间为1000毫秒，最小距离变化为0米
        locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

    }


}