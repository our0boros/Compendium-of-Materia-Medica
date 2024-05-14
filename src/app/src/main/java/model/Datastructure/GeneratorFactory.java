package model.Datastructure;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import model.JsonReader;

/**
 * @author: Haochen Gong
 * @description: 生成树的工厂类，封装了读取json文件，通过对应的树的生成器处理json文件，并生成对应的树的过程。
 **/
public class GeneratorFactory {
    public static RBTree<?> tree(Context context, DataType dataType, int resourceId) throws JSONException, IOException {
        // 读取文件数据
        JsonReader jsonReader = new JsonReader(context);
        ArrayList<JSONObject> arrayList = jsonReader.readJsonFromFile(resourceId);

        // 根据读取的数据类型创建对应的树的生成器，生成树
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
                throw new IllegalArgumentException("Invalid type: " + dataType);
        }
    }
}
