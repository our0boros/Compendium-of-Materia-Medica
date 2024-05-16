package model.Datastructure;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * @author Haochen Gong u7776634
 * plant tree generator
 * (processing plant's json data, generateTree() method can generate tree from the processed data)
 **/
public class PlantTreeGenerator implements TreeGenerator<Plant>{
    private final RBTree<Plant> plantRBTree;

    public PlantTreeGenerator() {
        this.plantRBTree = new RBTree<Plant>();
    }

    @Override
    public RBTree<Plant> generateTree(ArrayList<JSONObject> arrayList) {
        for(JSONObject jsonObject : arrayList){
            try {
                int id = jsonObject.getInt("id");
                String commonName = jsonObject.getString("common_name");
                String slug = jsonObject.getString("slug");
                String scientificName = jsonObject.getString("scientific_name");
                String image = jsonObject.getString("image_url");
                String genus = jsonObject.getString("genus");
                String family = jsonObject.getString("family");
                String description = jsonObject.getString("description");

                // Creating and inserting nodes
                Plant plant = new Plant(id,commonName,slug,scientificName,image,genus,family,description);
                // Set plant id as key
                plantRBTree.insert(id, plant);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return plantRBTree;
    }
}
