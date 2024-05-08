package com.example.compendiumofmateriamedica;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import model.DataType;
import model.GeneratorFactory;
import model.Plant;
import model.PlantTreeManager;
import model.Post;
import model.PostTreeManager;
import model.RBTree;
import model.RBTreeNode;
import model.TreeManager;
import model.User;


/**
 * @author: Haochen Gong
 * @description: 植物数据显示页面。
 **/
public class PlantDetailShow extends AppCompatActivity {

    private TextView commonName;
    private TextView slug;
    private TextView scientificName;
    private TextView genus;
    private TextView family;
    private TextView description;



    private RBTree<User> userTree;
    private RBTree<Plant> plantTree;
    private RBTree<Post> postTree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail_show);

        // 因为用了单例模式，LoginActivity已经实例化了，这些不需要了
//        // 运行加载数据的函数
//        try {
//            DataInitial();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        commonName = findViewById(R.id.common_name);
        slug = findViewById(R.id.slug);
        scientificName = findViewById(R.id.scientific_name);
        genus = findViewById(R.id.genus);
        family = findViewById(R.id.family);
        description = findViewById(R.id.description);

        PlantTreeManager plantTreeManager = PlantTreeManager.instance;
        ArrayList<RBTreeNode<Plant>> plants = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID,77116);
        RBTreeNode<Plant> node = plants.get(0);
        Plant plant = node.getValue();

        commonName.setText(plant.getCommonName());
        slug.setText(plant.getSlug());
        scientificName.setText(plant.getScientificName());
        genus.setText(plant.getGenus());
        family.setText(plant.getFamily());
        description.setText(plant.getDescription());
    }


    // 因为用了单例模式，LoginActivity已经实例化了，这些不需要了
//    /**
//     * @author: Haochen Gong
//     * @description: 加载数据
//     **/
//    private void DataInitial() throws JSONException, IOException {
//        userTree = (RBTree<User>) GeneratorFactory.tree(this, DataType.USER, R.raw.users);
//        plantTree = (RBTree<Plant>) GeneratorFactory.tree(this, DataType.PLANT, R.raw.plants);
//        postTree = (RBTree<Post>) GeneratorFactory.tree(this, DataType.POST, R.raw.posts);
//    }


}