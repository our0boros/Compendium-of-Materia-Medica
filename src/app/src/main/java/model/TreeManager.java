package model;

public interface TreeManager<T> {
    void insert(int key, T value);
    void delete(int key);
}
