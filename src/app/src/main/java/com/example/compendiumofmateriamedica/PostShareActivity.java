package com.example.compendiumofmateriamedica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.Manifest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.User;
import model.Plant_Identification;


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
    private String photoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_share);

        // 给User和图片赋值
        // 获取从上个activity处传来的User
        currentUser = (User) this.getIntent().getSerializableExtra("User");
        Log.d("SharePost", "Enter share page, user = " + currentUser.getUsername() + ".");
        // 获取从上个activity处传来的照片路径
        photoPath = getIntent().getStringExtra("photoPath");
        // 显示照片
        ImageView photo = findViewById(R.id.imageView_post_share_photo);
        MainActivity.loadImageFromURL(this, photoPath, photo, "Photo");
        // ======================================================================
        // 对接API
        // 创建网络请求线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String result = Plant_Identification.getPlantNetAPIResult(photoPath);
                String result = "{\"access_token\": \"VaoHnUbHUUTrlUq\", \"model_version\": \"plant_id:3.7.0\", \"custom_id\": null, \"input\": {\"latitude\": null, \"longitude\": null, \"images\": [\"https://plant.id/media/imgs/68e89f3331274854ae9eaab6cb9c3bab.jpg\"], \"datetime\": \"2024-05-09T12:51:06.506002+00:00\"}, \"result\": {\"is_plant\": {\"probability\": 0.005755537, \"threshold\": 0.5, \"binary\": false}, \"classification\": {\"suggestions\": [{\"id\": \"c4f244a57446113e\", \"name\": \"Kalanchoe\", \"probability\": 0.2058, \"details\": {\"language\": \"en\", \"entity_id\": \"c4f244a57446113e\"}}, {\"id\": \"052682b3b44e8310\", \"name\": \"Crassula ovata\", \"probability\": 0.1916, \"details\": {\"language\": \"en\", \"entity_id\": \"052682b3b44e8310\"}}, {\"id\": \"fb1f0f0b31562030\", \"name\": \"Dracaena trifasciata\", \"probability\": 0.1372, \"details\": {\"language\": \"en\", \"entity_id\": \"fb1f0f0b31562030\"}}, {\"id\": \"7201879bcfec317f\", \"name\": \"Albuca\", \"probability\": 0.122, \"details\": {\"language\": \"en\", \"entity_id\": \"7201879bcfec317f\"}}, {\"id\": \"62b9d1fe2846a316\", \"name\": \"Cucurbita pepo\", \"probability\": 0.1071, \"details\": {\"language\": \"en\", \"entity_id\": \"62b9d1fe2846a316\"}}, {\"id\": \"aa8668f208a31ae8\", \"name\": \"Curio\", \"probability\": 0.0965, \"details\": {\"language\": \"en\", \"entity_id\": \"aa8668f208a31ae8\"}}, {\"id\": \"9e6267a55d42384c\", \"name\": \"Ornithogalum\", \"probability\": 0.0555, \"details\": {\"language\": \"en\", \"entity_id\": \"9e6267a55d42384c\"}}, {\"id\": \"c1396f242d8786ff\", \"name\": \"Cucumis sativus\", \"probability\": 0.0441, \"details\": {\"language\": \"en\", \"entity_id\": \"c1396f242d8786ff\"}}, {\"id\": \"4ba05f1050481731\", \"name\": \"Aloe vera\", \"probability\": 0.0216, \"details\": {\"language\": \"en\", \"entity_id\": \"4ba05f1050481731\"}}, {\"id\": \"f3a94555ecc4cfde\", \"name\": \"Sedum \\u00d7 rubrotinctum\", \"probability\": 0.0186, \"details\": {\"language\": \"en\", \"entity_id\": \"f3a94555ecc4cfde\"}}]}}, \"status\": \"COMPLETED\", \"sla_compliant_client\": true, \"sla_compliant_system\": true, \"created\": 1715259066.506002, \"completed\": 1715259066.703937}";
                Log.println(Log.ASSERT, "API RESULT", result);
            }
        });
        thread.start(); // 启动线程

        // ======================================================================
        // post 内容
        EditText postContent = findViewById(R.id.editText_post_content);

        // 设置cancel按钮点击事件
        Button buttonCancel = findViewById(R.id.button_post_share_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("SharePost", "Cancel button clicked");
                showConfirmDialog();
            }
        });



        // 设置Post按钮点击事件
        Button buttonPost = findViewById(R.id.button_post_share_post);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // share post
                sharePost(postContent, photoPath);
                // go back to MainActivity
                Intent intent = new Intent(PostShareActivity.this, MainActivity.class);
                // 清除历史堆栈中MainActivity之上的所有activity并回到MainActivity，节省堆栈空间
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // 指定返回MainActivity中的SocialFragment
                intent.putExtra("navigate_fragment_id", R.id.navigation_social);
                intent.putExtra("User", currentUser);
                startActivity(intent);
                // TODO 这里回到mainactivity之后social的post列表应该刷新一遍,以最新发布时间显示，从而展示用户刚刚发布的post
            }
        });



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

    /**
     * 跳出一个确认窗口，点击确认会返回MainActivity，取消则什么都不做
     */
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Cancel");
        builder.setMessage("Are you sure you want to cancel editing?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 用户确认取消，返回主界面
                Intent intent = new Intent(PostShareActivity.this, MainActivity.class);
                // 清除历史堆栈中MainActivity之上的所有activity并回到MainActivity，节省堆栈空间
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("User", currentUser);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 用户选择留在当前页面，对话框消失，不进行任何操作
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void sharePost(EditText postContent, String photoURL){
        Log.d("SharePost", "Share post......");
        // 为生成post设置变量
        int postId = PostTreeManager.getInstance().getTreeSize() + 1;
        int uid = currentUser.getUser_id();
        int plantId = 5; // 这里随便给个plantid，实际拍到照片识别后再传进来就有了
        String photo = photoURL;
        String content = postContent.getText().toString();
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
        Date now = new Date();
        String timestamp = sdf.format(now);

        // 生成Post并加入到当前app的MainActivity的postTree中
        // TODO 从firebase中读取最新的posts
        Post post = new Post(postId, uid, plantId, photo, content, timestamp);
        PostTreeManager.getInstance().insert(post.getPost_id(), post);
        Log.d("SharePost", "Post added to the postTree in MainActivity");
    }
}