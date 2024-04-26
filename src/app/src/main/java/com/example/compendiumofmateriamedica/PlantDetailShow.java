package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PlantDetailShow extends AppCompatActivity {

    private TextView commonName;
    private TextView slug;
    private TextView scientificName;
    private TextView genus;
    private TextView family;
    private TextView description;


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


    }
}