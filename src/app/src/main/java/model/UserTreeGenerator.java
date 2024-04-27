package model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * @author: Haochen Gong
 * @description: user树的生成器（处理user的json数据，generateTree()方法可以将处理后的数据生成树）
 **/
public class UserTreeGenerator implements TreeGenerator<User> {

    private final RBTree<User> userRBTree;

    public UserTreeGenerator() {
        this.userRBTree = new RBTree<User>();
    }

    @Override
    public RBTree<User> generateTree(ArrayList<JSONObject> arrayList) {
        for(JSONObject jsonObject : arrayList){
            try {
                int id = jsonObject.getInt("user_id");
                String name = jsonObject.getString("username");
                String email = jsonObject.getString("email");
                String password = jsonObject.getString("password");
                String avatar = jsonObject.getString("avatar_url");

                // 创建并插入节点
                User user = new User(id,name,email,password,avatar);
                // 设置user id 作key
                userRBTree.insert(id, user);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userRBTree;
    }
}
