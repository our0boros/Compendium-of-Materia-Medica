package model;

/*
 * @author: Haochen Gong
 * 红黑树节点类
 */
public class RBTreeNode<V> {
    private final boolean RED = false;
    private final boolean BLACK = true;
    private int key;
    private V value;
    private boolean color;
    private RBTreeNode left;
    private RBTreeNode right;
    private RBTreeNode parent;

    public RBTreeNode(int key, V value) {
        this.key = key;
        this.color = RED;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public RBTreeNode getLeft() {
        return left;
    }

    public void setLeft(RBTreeNode left) {
        this.left = left;
    }

    public RBTreeNode getRight() {
        return right;
    }

    public void setRight(RBTreeNode right) {
        this.right = right;
    }

    public RBTreeNode getParent() {
        return parent;
    }

    public void setParent(RBTreeNode parent) {
        this.parent = parent;
    }

    public String toString() {
        return "RBTreeNode{" +
                ",key=" + key +
                ", value=" + value +
                ", color=" + color +
                '}';
    }
}
