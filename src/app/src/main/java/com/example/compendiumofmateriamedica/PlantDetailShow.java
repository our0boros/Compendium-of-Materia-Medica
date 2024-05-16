package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author: Haochen Gong, Hongjun Xu
 * @uid: u7776634 , u7733037
 * @datetime: 2024/05/16
 * @description: Display detailed introduction interface of plants
 **/
public class PlantDetailShow extends AppCompatActivity {

    private TextView commonName;
    private TextView slug;
    private TextView scientificName;
    private TextView genus;
    private TextView family;
    private TextView description;
    private int plantId;
    private ImageView plant_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail_show);

        commonName = findViewById(R.id.common_name);
        slug = findViewById(R.id.slug);
        scientificName = findViewById(R.id.scientific_name);
        genus = findViewById(R.id.genus);
        family = findViewById(R.id.family);
        description = findViewById(R.id.description);
        plant_image = findViewById(R.id.plant_image);

        // get plant id from last activity
        plantId = (int) this.getIntent().getSerializableExtra("PlantId");
        ArrayList<Plant> plants = PlantTreeManager.getInstance().search(PlantTreeManager.PlantInfoType.ID, String.valueOf(plantId));
        Plant plant = plants.get(0);


        commonName.setText(plant.getCommonName());
        slug.setText(plant.getSlug());
        scientificName.setText(plant.getScientificName());
        genus.setText(plant.getGenus());
        family.setText(plant.getFamily());
        description.setText(plant.getDescription());

        String url = plant.getImage();
        new FetchImageTask().execute(url);
    }

    private class FetchImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlString = strings[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream inputStream = urlConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            // 更新UI，显示从URL获取的图片
            if (bitmap != null) {
                plant_image.setImageBitmap(bitmap);
            }
        }
    }
}

