package model.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class FirebaseDataImporter {

    public void importJsonToFirebase(String json, String path) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postsRef = database.getReference(path);

        // 使用 Gson 解析 JSON 数据
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> postData = gson.fromJson(json, type);

        // 批量写入数据到 Firebase Database
        for (Map<String, Object> post : postData) {
            // 创建一个新的 post ID
            String postId = postsRef.push().getKey();
            if (postId != null) {
                postsRef.child(postId).setValue(post);
            }
        }
    }
}