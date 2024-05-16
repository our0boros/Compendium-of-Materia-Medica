package model.Datastructure;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Haochen Gong
 * Generate tree factory class , encapsulates the process of reading a json file ,
 * processing the json file through the corresponding tree generator , and generating the corresponding tree .
 **/
public class GeneratorFactory {
    public static RBTree<?> tree(Context context, DataType dataType, int resourceId) throws JSONException, IOException {
        // Read the file data
        JsonReader jsonReader = new JsonReader(context);
        ArrayList<JSONObject> arrayList = jsonReader.readJsonFromFile(resourceId);

        // A generator that creates a corresponding tree based on the type of data read, generating the tree
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
