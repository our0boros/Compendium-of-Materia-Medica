package model.Datastructure;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Haochen Gong
 * Data processing + tree generator interface
 **/
public interface TreeGenerator<T> {
    RBTree<T> generateTree(ArrayList<JSONObject> arrayList);
}


