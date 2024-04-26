package model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/*
 * @author: Haochen Gong
 * 处理post json数据并储存到树
 */
public class PostTreeGenerator implements TreeGenerator<Post>{

    private RBTree<Post> postRBTree;

    public PostTreeGenerator() {
        this.postRBTree = new RBTree<>();
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
                postRBTree.insert(plantId, post);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return postRBTree;
    }
}
