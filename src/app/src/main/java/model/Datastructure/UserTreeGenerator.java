package model.Datastructure;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * @author Haochen Gong
 * user tree generator
 * (process user's json data, generateTree() method can generate a tree from the processed data)）
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

                // Creating and inserting nodes
                User user = new User(id,name,email,password,avatar);
                // set user id as key
                userRBTree.insert(id, user);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userRBTree;
    }
}
