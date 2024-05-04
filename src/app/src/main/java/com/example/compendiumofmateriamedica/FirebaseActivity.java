package com.example.compendiumofmateriamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import model.UserFirebaseImporter;

public class FirebaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);


        UserFirebaseImporter importer = new UserFirebaseImporter(this);
        importer.createUsersFromJson(R.raw.users); // 假设文件名为users.json
    }
}