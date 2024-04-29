package model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * @author: Haochen Gong
 * @description: post树的生成器（处理post的json数据，generateTree()方法可以将处理后的数据生成树）
 **/
public class PostTreeGenerator implements TreeGenerator<Post>{

    private final RBTree<Post> postRBTree;

    public PostTreeGenerator() {
        this.postRBTree = new RBTree<Post>();
    }

    @Override
    public RBTree<Post> generateTree(ArrayList<JSONObject> arrayList) {
        for(JSONObject jsonObject : arrayList){
            try {
                int postId = jsonObject.getInt("post_id");
                int uid = jsonObject.getInt("user_id");
                int plantId = jsonObject.getInt("plant_id");
                String photo = jsonObject.getString("photo_url");
                String content = jsonObject.getString("content");
                String timestamp = jsonObject.getString("timestamp");

                // 创建并插入节点
                Post post = new Post(postId,uid,plantId,photo,content,timestamp);
                // 设置plant id 作key
                postRBTree.insert(postId, post);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return postRBTree;
    }
}
