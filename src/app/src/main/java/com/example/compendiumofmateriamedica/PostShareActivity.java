package com.example.compendiumofmateriamedica;

import static model.UtilsApp.loadImageFromURL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import model.Datastructure.User;
import model.Parser.Token;
import model.Parser.Tokenizer;
import model.PlantIdentification;


/**
 * @author: Haochen Gong, Xing Chen
 * @uid: u7733037, u7725171
 * @description:
 * Get information from last activity and can share post here
 * The plant can be recognized by an API and will show details in this page.
 * Also GPS location can be seen.
 **/
public class PostShareActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationText;
    private User currentUser;
    private String photoPath;

    Plant currentPlant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_share);

        // get user from last activity
        currentUser = (User) this.getIntent().getSerializableExtra("User");
        Log.d("SharePost", "Enter share page, user = " + currentUser.getUsername() + ".");
        // get photo path
        photoPath = getIntent().getStringExtra("photoPath");
        // show image
        ImageView photo = findViewById(R.id.imageView_post_share_photo);
        loadImageFromURL(this, photoPath, photo, "Photo");
        // click will show big picture
        photo.setOnClickListener(v -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(photoPath);
            photoDialogFragment.show(getSupportFragmentManager(), "photo_dialog");
        });
        // ======================================================================
        // API
        // create internet requirement thread
        ImageView plantImage = findViewById(R.id.toPostPlantImage);
        TextView plantCommonName = findViewById(R.id.toPostPlantCommonName);
        TextView plantSciName = findViewById(R.id.toPostSciName);
        TextView plantFamily = findViewById(R.id.toPostFamily);
        TextView plantDescription = findViewById(R.id.toPostDescription);

        Thread thread = new Thread(() -> {
            Log.println(Log.ASSERT, "START THREAD", "=================================== START THREAD =================================== ");
            Log.println(Log.ASSERT, "API INPUT", photoPath);
            String result = PlantIdentification.getPlantNetAPIResult(photoPath);
            Log.println(Log.ASSERT, "API RESULT", result);
            try {

                JSONObject jsonObject = new JSONObject(result);
                String sciName = (String) jsonObject.getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("species")
                        .get("scientificNameWithoutAuthor");
                Log.println(Log.ASSERT, "API RESULT", sciName);
                // look for current plant data
                ArrayList<Plant> plantTreeList = PlantTreeManager.getInstance().search(PlantTreeManager.PlantInfoType.SCIENTIFIC_NAME, sciName);
                // if found
                if (plantTreeList.size() > 0) {
                    currentPlant = plantTreeList.get(0);
                    Log.println(Log.ASSERT, "API RESULT", "Found mapping result: " + currentPlant);
                } else {
                    Log.println(Log.ASSERT, "API RESULT", "Do not found mapping result");
                    // if not, create one and insert
                    currentPlant = new Plant(
                            sciName.hashCode(),
                            (String) jsonObject.getJSONArray("results")
                                    .getJSONObject(0)
                                    .getJSONObject("species")
                                    .getJSONArray("commonNames")
                                    .get(0),
                            "no slug",
                            sciName,
                            PlantIdentification.getFromWiki(sciName, "image"),
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
                            PlantIdentification.getFromWiki(sciName, "content")
                    );
                    Log.println(Log.ASSERT, "API RESULT", "Create new result: " + currentPlant);
                    PlantTreeManager.getInstance().insert(currentPlant.getId(), currentPlant);
                }

                // update UI
                runOnUiThread(() -> {
                    if (currentPlant != null) {
                        Log.println(Log.ASSERT, "THREAD", "Update UI: \n" + currentPlant);
                        // prepare data
                        loadImageFromURL(this, currentPlant.getImage(), plantImage, "Photo");
                        plantImage.setOnClickListener(v -> {
                            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(currentPlant.getImage());
                            photoDialogFragment.show(getSupportFragmentManager(), "photo_dialog");
                        });

                        setTextViewContent(plantCommonName, "Common Name:", currentPlant.getCommonName());
                        setTextViewContent(plantSciName, "Scientific Name:", currentPlant.getScientificName());
                        setTextViewContent(plantFamily, "Family Name:", currentPlant.getFamily());
                        setTextViewContent(plantDescription, "Description:", currentPlant.getDescription());
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
        thread.start(); // start thread

        // ======================================================================
        // post content
        EditText postContent = findViewById(R.id.editText_post_content);

        // set cancel event
        Button buttonCancel = findViewById(R.id.button_post_share_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("SharePost", "Cancel button clicked");
                showConfirmDialog();
            }
        });



        // set post event
        Button buttonPost = findViewById(R.id.button_post_share_post);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if api still does not callback
                if(currentPlant == null){
                    // could not post
                    // show dialog
                    showCannotPostDialog();
                } else {
                    // share post
                    boolean success = sharePost(postContent, photoPath);
                    if (success) {
                        // go back to MainActivity
                        Intent intent = new Intent(PostShareActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        // go back to SocialFragment in MainActivity
                        intent.putExtra("navigate_fragment_id", R.id.navigation_social);
                        intent.putExtra("User", currentUser);
                        startActivity(intent);
                    }
                }
            }
        });



        // GPS part
        locationText = (TextView) findViewById(R.id.locationText);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // use Geocoder to create location information when location changed
                if (location != null) {
                    Geocoder geocoder = new Geocoder(PostShareActivity.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (!addressList.isEmpty()) {
                            Address address = addressList.get(0);
                            String addressText = address.getAddressLine(0); // get full location
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
                // if location denied
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        // if API 23 or higher，ask for location constantly
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
        }

        // set min time and distance
        locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

    }

    /**
     * Show a dialog, confirm to cancel post editing and go back
     */
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Cancel");
        builder.setMessage("Are you sure you want to cancel editing?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // if cancel
                Intent intent = new Intent(PostShareActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("User", currentUser);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // if stay
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    // prevent user from sharing post without plant information
    private void showCannotPostDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("-_- Cannot post now.");
        builder.setMessage("We are still figuring out what this plant is.\nAPI time out due to internet issue.");
        builder.setNegativeButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private boolean sharePost(EditText postContent, String photoURL){
        Log.d("SharePost", "Share post......");
        // create post
        int postId = PostTreeManager.getInstance().getTreeSize() + 1;
        int uid = currentUser.getUser_id();
        int plantId = currentPlant.getId();
        String photo = photoURL;
        String content = postContent.getText().toString();
        // now
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
        Date now = new Date();
        String timestamp = sdf.format(now);

        // create post and add it into post tree
        try {
            Tokenizer tokenizer = new Tokenizer(content, true);
            Post post = new Post(postId, uid, plantId, photo, tokenizer.getFullToken(), timestamp);
            PostTreeManager.getInstance().insert(post.getPost_id(), post);
            Log.d("SharePost", "Post added to the postTree in MainActivity");
            return true;
        } catch (Token.IllegalTokenException e) {
            Toast.makeText(getBaseContext(), "Invalid Token Please Try other words", Toast.LENGTH_LONG).show();
            return false;
        }

    }
    private void setTextViewContent(TextView textView, String label, String content){
        String text = label + "\n" + content;
        SpannableString spannableString = new SpannableString(text);
        // set font for title
        spannableString.setSpan(new RelativeSizeSpan(1.f), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // 加粗
        // set font for content
        int startIndexOfContent = text.indexOf(content);
        spannableString.setSpan(new RelativeSizeSpan(0.8f), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), startIndexOfContent, startIndexOfContent + content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // apply this to textview
        textView.setText(spannableString);
    }
}