package model.Datastructure;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Haochen Gong
 * @uid u7776634
 * @description: UserTree management method class
 **/
public class UserTreeManager implements TreeManager<User> {

    private final RBTree<User> userRBTree;
    public enum UserInfoType {
        ID, NAME, EMAIL, AVATAR;
    }
    private static UserTreeManager instance;
    private UserTreeManager(RBTree<User> userRBTree) {
        if (instance != null) {
            throw new IllegalStateException("Instance already created");
        }
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

    // External search interface, call this method to start a search
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

    // The actual recursive search method
    private  <T> void search(RBTreeNode<User> node, UserInfoType infoType, T info, ArrayList<User> users){
        // If the current node is null, it means that the child of the leaf node has been reached, and it is returned directly
        if (node == null) {
            return;
        }

        // If the value of the current node is equal to the searched value, add to the result list
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

        search(node.getLeft(), infoType, info, users);
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
            //Log.d("UserTreeManager", "Find User: " + foundUser.getUsername());
            return foundUser;
        } else {
            //Log.w("UserTreeManager", "Do not find user.");
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

