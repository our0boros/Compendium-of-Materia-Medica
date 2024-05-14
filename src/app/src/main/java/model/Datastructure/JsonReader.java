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
 * @author: Haochen Gong
 * @description: json文件读取，返回jsonObject的arraylist
 **/
public class JsonReader {

    private final Context context;

    public JsonReader(Context context) {
        this.context = context;
    }

    public ArrayList<JSONObject> readJsonFromFile(int resourceId) throws IOException, JSONException {

        ArrayList<JSONObject> data = new ArrayList<>();

        // 打开res/raw中的资源文件作为InputStream
        InputStream is = context.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // 读取文件储存为一整个字符串
        StringBuilder jsonBuilder = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            jsonBuilder.append(line.trim()); // 去掉行首尾的空白字符
            line = br.readLine();
        }

        // 将完整的文件内容解析为JSONArray
        JSONArray jsonArray = new JSONArray(jsonBuilder.toString());

        // 遍历JSONArray，将每个元素添加到ArrayList中
        for (int i = 0; i < jsonArray.length(); i++) {
            data.add(jsonArray.getJSONObject(i));
        }

        // 关闭BufferedReader和InputStream
        br.close();
        is.close();

        return data;
    }
}