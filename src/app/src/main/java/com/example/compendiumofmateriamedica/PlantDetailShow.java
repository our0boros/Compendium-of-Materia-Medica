package com.example.compendiumofmateriamedica;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import model.DataType;
import model.GeneratorFactory;
import model.Plant;
import model.RBTree;
import model.RBTreeNode;

public class PlantDetailShow extends AppCompatActivity {

    private TextView commonName;
    private TextView slug;
    private TextView scientificName;
    private TextView genus;
    private TextView family;
    private TextView description;



    private RBTree<?> userTree;
    private RBTree<?> plantTree;
    private RBTree<?> postTree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail_show);


        // 运行加载数据的函数
        try {
            DataInitial();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        commonName = findViewById(R.id.common_name);
        slug = findViewById(R.id.slug);
        scientificName = findViewById(R.id.scientific_name);
        genus = findViewById(R.id.genus);
        family = findViewById(R.id.family);
        description = findViewById(R.id.description);

        RBTreeNode<Plant> node = (RBTreeNode<Plant>) plantTree.search(77116);
        Plant plant = node.getValue();
        commonName.setText(plant.getCommonName());
        slug.setText(plant.getSlug());
        scientificName.setText(plant.getScientificName());
        genus.setText(plant.getGenus());
        family.setText(plant.getFamily());
        description.setText(plant.getDescription());
    }



    /**
     * @author: Haochen Gong
     * 加载数据
     **/
    private void DataInitial() throws JSONException, IOException {
        userTree = GeneratorFactory.tree(this, DataType.USER, R.raw.users);
        plantTree = GeneratorFactory.tree(this, DataType.PLANT, R.raw.plants);
        postTree = GeneratorFactory.tree(this, DataType.POST, R.raw.posts);
    }


}