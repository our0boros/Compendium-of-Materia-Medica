package model;

import org.json.JSONObject;

import java.util.ArrayList;

/*
 * @author: Haochen Gong
 * 生成树的工厂类
 */
public class GeneratorFactory {
    public static RBTree<?> tree(DataType dataType, ArrayList<JSONObject> arrayList){
        switch (dataType) {
            case USER:
                UserTreeGenerator userTreeGenerator = new UserTreeGenerator();
                return userTreeGenerator.generateTree(arrayList);
            case PLANT:
                PlantTreeGenerator plantTreeGenerator = new PlantTreeGenerator();
                return plantTreeGenerator.generateTree(arrayList);
            case POST:
                PostTreeGenerator postTreeGenerator = new PostTreeGenerator();
                return postTreeGenerator.generateTree(arrayList);
            default:
                throw new IllegalArgumentException("No parser available for type: " + dataType);
        }
    }
}
