package model.Datastructure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private static PostTreeManager instance;
    private PostTreeManager(RBTree<Post> postRBTree) {
        this.postRBTree = postRBTree;
    }
    public static synchronized PostTreeManager getInstance(RBTree<Post> postRBTree) {
        if (instance == null) {
            instance = new PostTreeManager(postRBTree);
        }
        return instance;
    }
    // getter
    public static PostTreeManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Instance not created. Call getInstance(RBTree<Post>) first.");
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
    public interface PostCallback {
        void onCallback(List<Post> posts);
    }
    // 根据时间戳获取指定数量post
    public void getNewestPosts(int numberOfPosts, String lastLoadedPostTimestamp, PostCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Post> beforePosts = new ArrayList<>();

                if (lastLoadedPostTimestamp == null) {
                    // 如果没有提供时间戳,获取最新的帖子
                    getLatestPostsFromRBTree(postRBTree.root, numberOfPosts, beforePosts);
                } else {
                    // 获取指定时间戳之前的帖子
                    getBeforePosts(postRBTree.root, lastLoadedPostTimestamp, beforePosts);  // 获取所有时间在指定时间之前的post
                    // 按时间戳降序排列
                    beforePosts.sort(new Comparator<Post>() {
                        @Override
                        public int compare(Post p1, Post p2) {
                            return LocalDateTime.parse(p2.getTimestamp()).compareTo(LocalDateTime.parse(p1.getTimestamp()));
                        }
                    });
                }

                // 不足需要的数量时,全部返回,足够时返回指定数量
                if (beforePosts.size() <= numberOfPosts) {
                    callback.onCallback(beforePosts);
                } else {
                    callback.onCallback(new ArrayList<>(beforePosts.subList(0, numberOfPosts)));
                }
            }
        }).start();
//        ArrayList<Post> beforePosts = new ArrayList<>();
//        getBeforePosts(this.postRBTree.root, lastLoadedPostTimestamp, beforePosts);  // 获取所有时间在指定时间之前的post
//        // 按时间戳降序排列
//        beforePosts.sort(new Comparator<Post>() {
//            @Override
//            public int compare(Post p1, Post p2) {
//                return LocalDateTime.parse(p2.getTimestamp()).compareTo(LocalDateTime.parse(p1.getTimestamp()));
//            }
//        });
//
//        // 不足需要的数量时，全部返回，足够时返回指定数量
//        if (beforePosts.size() <= numberOfPosts) {
//            return beforePosts;
//        } else {
//            return new ArrayList<Post>(beforePosts.subList(0, numberOfPosts));
//        }
    }

    // 找到所有发布时间在输入时间之前的post
    private void getBeforePosts(RBTreeNode<Post> node, String timeStamp, ArrayList<Post> posts) {
        if (node != null) {
            if (LocalDateTime.parse(node.getValue().getTimestamp()).isBefore(LocalDateTime.parse(timeStamp))) {
                posts.add(node.getValue());  // 当前节点时间在输入时间之前时，添加到结果列表
            }
            getBeforePosts(node.getLeft(), timeStamp, posts);  // 访问左子树
            getBeforePosts(node.getRight(), timeStamp, posts);  // 访问右子树
        }
    }
    private void getLatestPostsFromRBTree(RBTreeNode<Post> node, int numberOfPosts, ArrayList<Post> latestPosts) {
        if (node != null && latestPosts.size() < numberOfPosts) {
            getLatestPostsFromRBTree(node.getRight(), numberOfPosts, latestPosts);
            if (latestPosts.size() < numberOfPosts) {
                latestPosts.add(node.getValue());
                getLatestPostsFromRBTree(node.getLeft(), numberOfPosts, latestPosts);
            }
        }
    }

    public int getTreeSize(){
        return postRBTree.size();
    }

}
