package model;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author: Haochen Gong
 * @description: post树的管理方法类
 **/
public class PostTreeManager implements TreeManager<Post>{
    private final RBTree<Post> postRBTree;
    public enum PostInfoType {
        POST_ID, UID, PLANT_ID, TIME, CONTENT;
    }
    // singleton design pattern
    public static PostTreeManager instance;
    private PostTreeManager(RBTree<Post> postRBTree) {
        this.postRBTree = postRBTree;
    }
    public static synchronized PostTreeManager getInstance(RBTree<Post> postRBTree) {
        if (instance == null) {
            instance = new PostTreeManager(postRBTree);
        }
        return instance;
    }
    @Override
    public void insert(int postId, Post post) {
        this.postRBTree.insert(postId, post);
    }

    @Override
    public void delete(int plantId) {
        this.postRBTree.delete(plantId);
    }

    // 对外的搜索接口，调用这个方法来开始搜索
    public <T> ArrayList<RBTreeNode<Post>> search (PostInfoType infoType, T info) {

        ArrayList<RBTreeNode<Post>> posts = new ArrayList<>();

        if (infoType == PostInfoType.POST_ID) {
            RBTreeNode<Post> post = this.postRBTree.search(Integer.parseInt((String) info));
            if(post != null) {
                posts.add(post);
            }
        } else {
            search(this.postRBTree.root, infoType, info, posts);
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
                if (node.getValue().getUser_id() == Integer.parseInt((String) info)) {
                    posts.add(node);
                }
                break;
            case PLANT_ID:
                if (node.getValue().getPlant_id() == Integer.parseInt((String) info)) {
                    posts.add(node);
                }
                break;
            // 需要提前封装timestamp的处理（这里只是简单的判断了post对象储存的时间戳是否完全一致）
            case TIME:
                if (node.getValue().getTimestamp().contains((CharSequence) info)) {
                    posts.add(node);
                }
                break;
            // 查找内容里是否含有某字符
            case CONTENT:
                String content = node.getValue().getContent(); // 转换成小写字母
                if (content.toLowerCase().contains((CharSequence) info)) {
                    posts.add(node);
                }
                break;
        }

        // 继续在左子树中递归搜索
        search(node.getLeft(), infoType, info, posts);
        // 继续在右子树中递归搜索
        search(node.getRight(), infoType, info, posts);
    }

    public int getTreeSize(){
        return postRBTree.size();
    }

}
