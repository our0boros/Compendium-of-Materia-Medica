package model.Datastructure;

/**
 * @author: Haochen Gong
 * @description: 红黑树节点类
 **/
public class RBTreeNode<V> {
    private final boolean RED = false;
    private final boolean BLACK = true;
    private int key;
    private V value;
    private boolean color;
    private RBTreeNode<V> left;
    private RBTreeNode<V> right;
    private RBTreeNode<V> parent;

    public RBTreeNode(int key, V value) {
        this.key = key;
        this.value = value;
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

    public RBTreeNode<V> getLeft() {
        return left;
    }

    public void setLeft(RBTreeNode<V> left) {
        this.left = left;
    }

    public RBTreeNode<V> getRight() {
        return right;
    }

    public void setRight(RBTreeNode<V> right) {
        this.right = right;
    }

    public RBTreeNode<V> getParent() {
        return parent;
    }

    public void setParent(RBTreeNode<V> parent) {
        this.parent = parent;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value){
        this.value = value;
    }

    public String toString() {
        return "RBTreeNode{" +
                ",key=" + key +
                ", value=" + value +
                ", color=" + color +
                '}';
    }
}
