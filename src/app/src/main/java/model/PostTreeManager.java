package model;

/*
 * @author: Haochen Gong
 * post树的管理方法类
 */
public class PostTreeManager implements TreeManager<Post>{

    private final RBTree<Post> postRBTree;

    public PostTreeManager(RBTree<Post> postRBTree) {
        this.postRBTree = postRBTree;
    }

    @Override
    public void insert(int plantId, Post post) {
        this.postRBTree.insert(plantId, post);
    }

    @Override
    public void delete(int plantId) {
        this.postRBTree.delete(plantId);
    }

    public RBTreeNode<Post> searchByPlantID(int plantId) {
        return this.postRBTree.search(plantId);
    }

    // 对外的搜索接口，调用这个方法来开始搜索
    public RBTreeNode<Post> searchByUID(int uid) {
        return searchByUID(postRBTree.root, uid);
    }

    // 实际的递归搜索方法
    private RBTreeNode<Post> searchByUID(RBTreeNode<Post> node, int uid) {
        // 如果当前节点是null，说明已经到达了叶子节点的子节点，返回null表示没有找到
        if (node == null) {
            return null;
        }

        // 如果当前节点的值与搜索的值相等，返回当前节点
        if(node.getValue().getUid() == uid){
            return node;
        }

        // 在左子树中递归搜索
        RBTreeNode<Post> left = searchByUID(node.getLeft(), uid);
        // 如果在左子树中找到了，就返回找到的节点
        if (left != null) {
            return left;
        }

        // 在右子树中递归搜索
        return searchByUID(node.getRight(), uid);
    }


}
