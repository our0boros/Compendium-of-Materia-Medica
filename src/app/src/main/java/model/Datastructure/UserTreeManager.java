package model.Datastructure;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: Haochen Gong
 * @description: user树的管理方法类
 **/
public class UserTreeManager implements TreeManager<User> {

    private final RBTree<User> userRBTree;
    public enum UserInfoType {
        ID, NAME, EMAIL, AVATAR;
    }
    private static UserTreeManager instance;
    private UserTreeManager(RBTree<User> userRBTree) {
        this.userRBTree = userRBTree;
    }
    public static synchronized UserTreeManager getInstance(RBTree<User> userRBTree){
        if(instance == null){
            instance = new UserTreeManager(userRBTree);
        }
        return instance;
    }
    public static synchronized UserTreeManager getInstance(){
        if(instance == null){
            throw new IllegalStateException("Instance not created. Call getInstance(RBTree<User>) first.");
        }
        return instance;
    }

    @Override
    public void insert(int id, User user) {
        userRBTree.insert(id, user);
    }

    @Override
    public void delete(int id) {
        userRBTree.delete(id);
    }

    // 对外的搜索接口，调用这个方法来开始搜索
    public <T> ArrayList<User> search(UserInfoType infoType, T info){

        ArrayList<User> users = new ArrayList<>();

        if (infoType == UserInfoType.ID) {
            RBTreeNode<User> user = userRBTree.search((int)info);
            if (user != null) {
                users.add(user.getValue());
            }
        } else {
            search(userRBTree.root, infoType, info, users);
        }

        return users;
    }

    // 实际的递归搜索方法
    private  <T> void search(RBTreeNode<User> node, UserInfoType infoType, T info, ArrayList<User> users){
        // 如果当前节点是null，说明已经到达了叶子节点的子节点，直接返回
        if (node == null) {
            return;
        }

        // 如果当前节点的值与搜索的值相等，加入结果列表
        switch (infoType) {
            case NAME:
                if (node.getValue().getUsername().equals(info)) {
                    users.add(node.getValue());
                }
            case EMAIL:
                if (node.getValue().getEmail().equals(info)) {
                    users.add(node.getValue());
                }
            case AVATAR:
                if (node.getValue().getAvatar_url().equals(info)) {
                    users.add(node.getValue());
                }
        }

        // 继续在左子树中递归搜索
        search(node.getLeft(), infoType, info, users);
        // 继续在右子树中递归搜索
        search(node.getRight(), infoType, info, users);
    }

    public List<User> getAllUser() {
        return this.userRBTree.getAllElements();
    }

    public User findUserById(int uid){
        // Check the initialization of UserTreeManager
        if (checkManagerInitial()==false){
            return null;
        }
        // Search user with given uid
        ArrayList<User> users = instance.search(UserTreeManager.UserInfoType.ID, uid);
        // Check user validation, uid is unique
        if (!users.isEmpty()) {
            User foundUser = users.get(0);
            Log.d("UserTreeManager", "Find User: " + foundUser.getUsername());
            return foundUser;
        } else {
            Log.w("UserTreeManager", "Do not find user.");
            return null;
        }
    }

    public boolean checkManagerInitial(){
        // Check the initialization of UserTreeManager
        if (instance == null) {
            Log.w("UserTreeManager", "UserTreeManager has not been initialized");
            return false;
        }
        return true;
    }



}

