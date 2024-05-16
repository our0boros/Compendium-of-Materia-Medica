package model.Datastructure;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Haochen Gong
 * @uid u7776634
 * @description: json file read, return jsonObject arraylist
 **/
public class JsonReader {

    private final Context context;

    public JsonReader(Context context) {
        this.context = context;
    }

    public ArrayList<JSONObject> readJsonFromFile(int resourceId) throws IOException, JSONException {

        ArrayList<JSONObject> data = new ArrayList<>();

        // Open the resource file in res/raw as InputStream
        InputStream is = context.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Reads a file and stores it as a whole string
        StringBuilder jsonBuilder = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            jsonBuilder.append(line.trim()); // 去掉行首尾的空白字符
            line = br.readLine();
        }

        // Parses the complete file contents into a JSONArray
        JSONArray jsonArray = new JSONArray(jsonBuilder.toString());

        // Iterate through the JSONArray, adding each element to the ArrayList
        for (int i = 0; i < jsonArray.length(); i++) {
            data.add(jsonArray.getJSONObject(i));
        }

        // Close BufferedReader and InputStream
        br.close();
        is.close();

        return data;
    }
}