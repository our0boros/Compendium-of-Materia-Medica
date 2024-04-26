package model;

/*
 * @author: Haochen Gong
 * 管理树的方法类的泛型接口
 */
public interface TreeManager<T> {
    void insert(int key, T value);
    void delete(int key);
}
