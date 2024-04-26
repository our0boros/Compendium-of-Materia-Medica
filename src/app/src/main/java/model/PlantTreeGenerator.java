package model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/*
 * @author: Haochen Gong
 * plant树的生成器（处理plant的json数据，generateTree()方法可以将处理后的数据生成树）
 */
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

                // 创建并插入节点
                Plant plant = new Plant(id,commonName,slug,scientificName,image,genus,family,description);
                // 设置plant id 作key
                plantRBTree.insert(id, plant);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return plantRBTree;
    }
}
