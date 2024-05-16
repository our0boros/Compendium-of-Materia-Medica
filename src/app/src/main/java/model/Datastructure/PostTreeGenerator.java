package model.Datastructure;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import model.Parser.Tokenizer;

/**
 * @author Haochen Gong
 * post tree generator
 * (processing post json data, generateTree() method can generate a tree from the processed data)
 **/
public class PostTreeGenerator implements TreeGenerator<Post>{

    private final RBTree<Post> postRBTree;

    public PostTreeGenerator() {
        this.postRBTree = new RBTree<Post>();
    }

    @Override
    public RBTree<Post> generateTree(ArrayList<JSONObject> arrayList) {
        for(JSONObject jsonObject : arrayList){
            try {
                int postId = jsonObject.getInt("post_id");
                int uid = jsonObject.getInt("user_id");
                int plantId = jsonObject.getInt("plant_id");
                String photo = jsonObject.getString("photo_url");
                String content = jsonObject.getString("content");
                String timestamp = jsonObject.getString("timestamp");

                // analyze likes
                List<Integer> likes = new ArrayList<>();
                JSONArray likesArray = jsonObject.getJSONArray("likes");
                for (int i = 0; i < likesArray.length(); i++) {
                    likes.add(likesArray.getInt(i));
                }

                // analyze likesRecord
                List<Integer> likesRecord = new ArrayList<>();
                JSONArray likesRecordArray = jsonObject.getJSONArray("likesRecord");
                for (int i = 0; i < likesRecordArray.length(); i++) {
                    likesRecord.add(likesRecordArray.getInt(i));
                }

                // analyze comments
                Map<Integer, String> comments = new LinkedHashMap<>();
                JSONObject commentsObject = jsonObject.getJSONObject("comments");
                Iterator<String> keys = commentsObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = commentsObject.getString(key);
                    comments.put(Integer.parseInt(key), value);
                }

                // Creating and inserting nodes
                Tokenizer tokenizer = new Tokenizer(content, true);
                Post post = new Post(postId,uid,plantId,photo,tokenizer.getFullToken(),timestamp,likes,likesRecord,comments);
                if(post.getLikes() == null
                        || post.getLikesRecord() == null
                        || post.getComments() == null)
                    Log.w("TreeGenerator", "Post id:" + post.getPost_id() + " has null attributes");
//                else
//                    Log.d("TreeGenerator", "Post id:" + post.getPost_id() + " generated with full attributes");
                // set post id as key
                postRBTree.insert(postId, post);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return postRBTree;
    }
}
