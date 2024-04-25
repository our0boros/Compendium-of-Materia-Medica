package model;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/*
* @author: Haochen Gong
* 处理user json数据并储存到树
*/
public class UserTreeGenerator implements TreeGenerator<User> {
    @Override
    public RBTree<User> tree(ArrayList<JSONObject> arrayList) {
        RBTree<User> userRBTree = new RBTree<>();
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
