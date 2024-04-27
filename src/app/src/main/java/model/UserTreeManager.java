package model;

/**
 * @author: Haochen Gong
 * @description: user树的管理方法类
 **/
public class UserTreeManager implements TreeManager<User> {

    private final RBTree<User> userRBTree;

    public UserTreeManager(RBTree<User> userRBTree) {
        this.userRBTree = userRBTree;
    }

    @Override
    public void insert(int id, User user) {
        userRBTree.insert(id, user);
    }

    @Override
    public void delete(int id) {
        userRBTree.delete(id);
    }

    public RBTreeNode<User> searchByUID(int id) {
        return userRBTree.search(id);
    }

}

