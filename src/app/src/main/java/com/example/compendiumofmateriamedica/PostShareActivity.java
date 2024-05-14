package com.example.compendiumofmateriamedica;

import static model.UtilsApp.loadImageFromURL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.Manifest;
import android.widget.Toast;

import com.example.compendiumofmateriamedica.ui.social.PhotoDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
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

    Plant currentPlant;
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
        loadImageFromURL(this, photoPath, photo, "Photo");
        // 点击显示大图
        photo.setOnClickListener(v -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(photoPath);
            photoDialogFragment.show(getSupportFragmentManager(), "photo_dialog");
        });
        // ======================================================================
        // 对接API
        // 创建网络请求线程
        ImageView plantImage = findViewById(R.id.toPostPlantImage);
        TextView plantCommonName = findViewById(R.id.toPostPlantCommonName);
        TextView plantSciName = findViewById(R.id.toPostSciName);
        TextView plantFamily = findViewById(R.id.toPostFamily);
        TextView plantDescription = findViewById(R.id.toPostDescription);

        Thread thread = new Thread(() -> {
            Log.println(Log.ASSERT, "START THREAD", "=================================== START THREAD =================================== ");
            Log.println(Log.ASSERT, "API INPUT", photoPath);
            String result = Plant_Identification.getPlantNetAPIResult(photoPath);
            Log.println(Log.ASSERT, "API RESULT", result);
            try {

                JSONObject jsonObject = new JSONObject(result);
                String sciName = (String) jsonObject.getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("species")
                        .get("scientificNameWithoutAuthor");
                Log.println(Log.ASSERT, "API RESULT", sciName);
                // 查找当前的plant库
                ArrayList<RBTreeNode<Plant>> plantTreeList = PlantTreeManager.getInstance().search(PlantTreeManager.PlantInfoType.SCIENTIFIC_NAME, sciName);
                // 如果找到匹配的
                if (plantTreeList.size() > 0) {
                    currentPlant = plantTreeList.get(0).getValue();
                    Log.println(Log.ASSERT, "API RESULT", "Found mapping result: " + currentPlant);
                } else {
                    Log.println(Log.ASSERT, "API RESULT", "Do not found mapping result");
                    // 如果不存在就塞进去
                    currentPlant = new Plant(
                            sciName.hashCode(),
                            (String) jsonObject.getJSONArray("results")
                                    .getJSONObject(0)
                                    .getJSONObject("species")
                                    .getJSONArray("commonNames")
                                    .get(0),
                            "no slug",
                            sciName,
                            Plant_Identification.getFromWiki(sciName, "image"),
                            (String) jsonObject.getJSONArray("results")
                                    .getJSONObject(0)
                                    .getJSONObject("species")
                                    .getJSONObject("genus")
                                    .get("scientificNameWithoutAuthor"),
                            (String) jsonObject.getJSONArray("results")
                                    .getJSONObject(0)
                                    .getJSONObject("species")
                                    .getJSONObject("family")
                                    .get("scientificNameWithoutAuthor"),
                            Plant_Identification.getFromWiki(sciName, "content")
                    );
                    Log.println(Log.ASSERT, "API RESULT", "Create new result: " + currentPlant);
                    PlantTreeManager.getInstance().insert(currentPlant.getId(), currentPlant);
                }

                // 在获取到植物信息后更新UI
                runOnUiThread(() -> {
                    if (currentPlant != null) {
                        Log.println(Log.ASSERT, "THREAD", "Update UI: \n" + currentPlant);
                        // 准备要展示的植物资料
                        loadImageFromURL(this, currentPlant.getImage(), plantImage, "Photo");
                        plantImage.setOnClickListener(v -> {
                            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(currentPlant.getImage());
                            photoDialogFragment.show(getSupportFragmentManager(), "photo_dialog");
                        });

                        setTextViewContent(plantCommonName, "Common Name:", currentPlant.getCommonName());
                        setTextViewContent(plantSciName, "Scientific Name:", currentPlant.getScientificName());
                        setTextViewContent(plantFamily, "Family Name:", currentPlant.getFamily());
                        setTextViewContent(plantDescription, "Description:", currentPlant.getDescription());
                        // Common name
                        // 创建一个SpannableString对象
//                        String label = "Common Name:";
//                        String content = currentPlant.getCommonName();
//                        String text = label + "\n" + content;
//                        SpannableString spannableString = new SpannableString(text);
//                        // 为“Common Name:”设置一个大号字体样式
//                        spannableString.setSpan(new RelativeSizeSpan(1.f), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // 加粗
//                        // 为内容设置一个小号字体样式
//                        int startIndexOfContent = text.indexOf(content);
//                        spannableString.setSpan(new RelativeSizeSpan(1.f), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // 蓝色字体
//                        // 应用这个SpannableString到TextView
//                        plantCommonName.setText(spannableString);

//                        plantSciName.setText("Scientific Name:\n" + currentPlant.getScientificName());
                        // 创建一个SpannableString对象
//                        label = "Scientific Name:";
//                        content = currentPlant.getScientificName();
//                        text = label + "\n" + content;
//                        spannableString = new SpannableString(text);
//                        // 为“Common Name:”设置一个大号字体样式
//                        spannableString.setSpan(new RelativeSizeSpan(1.f), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // 加粗
//                        // 为内容设置一个小号字体样式
//                        startIndexOfContent = text.indexOf(content);
//                        spannableString.setSpan(new RelativeSizeSpan(1.f), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // 蓝色字体
//                        // 应用这个SpannableString到TextView
//                        plantSciName.setText(spannableString);

//                        plantFamily.setText("Family Name:\n" + currentPlant.getFamily());

//                        plantDescription.setText("Description:\n" + currentPlant.getDescription());
                    }
                });
            } catch (JSONException e){
                Log.d("Error", e.toString());
                runOnUiThread(() -> {
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.no_result_from_api), Toast.LENGTH_LONG).show();
                    finish();
                });

            }
        });
        thread.start(); // 启动线程
//        if (currentPlant != null) {
//
//            Log.println(Log.ASSERT, "THREAD", "Update UI: \n" + currentPlant);
//            // 准备要展示的植物资料
//            MainActivity.loadImageFromURL(this, currentPlant.getImage(), plantImage, "Photo");
//            plantCommonName.setText(currentPlant.getCommonName());
//            plantSciName.setText(currentPlant.getScientificName());
//            plantFamily.setText(currentPlant.getFamily());
//            plantDescription.setText(currentPlant.getDescription());
//        }
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
        Post post = new Post(postId, uid, plantId, photo, content, timestamp);
        PostTreeManager.getInstance().insert(post.getPost_id(), post);
        Log.d("SharePost", "Post added to the postTree in MainActivity");
    }
    private void setTextViewContent(TextView textView, String label, String content){
        // 创建一个SpannableString对象
        String text = label + "\n" + content;
        SpannableString spannableString = new SpannableString(text);
        // 为“Common Name:”设置一个大号字体样式
        spannableString.setSpan(new RelativeSizeSpan(1.f), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // 加粗
        // 为内容设置一个小号字体样式
        int startIndexOfContent = text.indexOf(content);
        spannableString.setSpan(new RelativeSizeSpan(0.8f), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 应用这个SpannableString到TextView
        textView.setText(spannableString);
    }
}