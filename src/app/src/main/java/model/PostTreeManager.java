package model;

import java.util.ArrayList;

/**
 * @author: Haochen Gong
 * @description: post树的管理方法类
 **/
public class PostTreeManager implements TreeManager<Post>{

    private final RBTree<Post> postRBTree;
    public enum PostInfoType {
        POST_ID, UID, PLANT_ID;
    }

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

    // 对外的搜索接口，调用这个方法来开始搜索
    public <T> ArrayList<RBTreeNode<Post>> search (PostInfoType infoType, T info) {

        ArrayList<RBTreeNode<Post>> posts = new ArrayList<>();

        if (infoType == PostInfoType.POST_ID) {
            RBTreeNode<Post> post = postRBTree.search((int)info);
            if(post != null) {
                posts.add(post);
            }
        } else {
            search(postRBTree.root, infoType, info, posts);
        }

        return posts;
    }

    // 实际的递归搜索方法
    private  <T> void search(RBTreeNode<Post> node, PostInfoType infoType, T info, ArrayList<RBTreeNode<Post>> posts) {
        // 如果当前节点是null，说明已经到达了叶子节点的子节点，直接返回
        if (node == null) {
            return;
        }

        // 如果当前节点的值与搜索的值相等，加入结果列表
        switch (infoType) {
            case UID:
                if (node.getValue().getUid() == (int)info) {
                    posts.add(node);
                }
            case PLANT_ID:
                if (node.getValue().getPlantId() == (int)info) {
                    posts.add(node);
                }
        }

        // 继续在左子树中递归搜索
        search(node.getLeft(), infoType, info, posts);
        // 继续在右子树中递归搜索
        search(node.getRight(), infoType, info, posts);
    }



}
