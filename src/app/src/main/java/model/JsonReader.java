package model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * @author: Haochen Gong
 * json文件读取，返回jsonObject的arraylist
 */
public class JsonReader {

    private final Context context;

    public JsonReader(Context context) {
        this.context = context;
    }

    public ArrayList<JSONObject> readJsonFromFile(int resourceId) throws IOException, JSONException {

        ArrayList<JSONObject> arrayList = new ArrayList<>();

        // 打开res/raw中的资源文件作为InputStream
        InputStream is = context.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // 逐行读取并转换为JSONObject,存入array
        String line = br.readLine();
        while (line != null) {
            arrayList.add(new JSONObject(line));
            line = br.readLine();
        }

        // 关闭BufferedReader和InputStream
        br.close();
        is.close();

        return arrayList;
    }
}