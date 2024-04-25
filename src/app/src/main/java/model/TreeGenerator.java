package model;

import org.json.JSONObject;

import java.util.ArrayList;

/*
 * @author: Haochen Gong
 * 数据处理+树的生成器接口
 */
public interface TreeGenerator<T> {
    RBTree<T> tree(ArrayList<JSONObject> arrayList);
}


