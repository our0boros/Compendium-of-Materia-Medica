package model;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * @author: Haochen Gong
 * json文件读取，返回jsonObject的arraylist
 */
public class JsonReader {
    public ArrayList<JSONObject> readJsonFromFile(String filePath) throws IOException, JSONException {

        ArrayList<JSONObject> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // 逐行读取并转换为JSONObject,存入array
            String line = br.readLine();
            while (line != null) {
                arrayList.add(new JSONObject(line));
                line = br.readLine();
            }
        }

        return arrayList;
    }
}