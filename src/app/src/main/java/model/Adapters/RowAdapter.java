package model.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {
    private Context context;
    private ArrayList<Integer> data;

    public RowAdapter(Context context, ArrayList<Integer> data) throws JSONException, IOException {
        this.context = context;
        this.data = data;
    }

    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_item, parent, false);
        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        ArrayList<RBTreeNode<Post>> nodes = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.POST_ID, String.valueOf(data.get(position)));
        Log.println(Log.ASSERT, "DEBUG", "[GridAdapter] onBindViewHolder: nodes size " + nodes.size());
        // 加载Post图片
        String postURL = nodes.get(0).getValue().getPhoto_url();
        MainActivity.loadImageFromURL(this.context, postURL, holder.postImage, "Photo");
        // 加载用户图片
        RBTreeNode<User> user = UserTreeManager.getInstance().search(UserTreeManager.UserInfoType.ID, nodes.get(0).getValue().getUser_id()).get(0);
        String userURL = user.getValue().getAvatar_url();
        Log.println(Log.ASSERT, "DEBUG", "[GridAdapter] onBindViewHolder: user avatar: " + userURL);
        MainActivity.loadImageFromURL(this.context, userURL, holder.userImage, "Avatar");
        // 加载用户名字
        holder.userName.setText(user.getValue().getUsername());

        String postContent = nodes.get(0).getValue().getContent();
        holder.postContent.setText(postContent);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        public ImageView postImage;
        public ImageView userImage;
        public TextView userName;
        public TextView postContent;


        public RowViewHolder(View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.rowLayoutPostImage);
            userImage = itemView.findViewById(R.id.rowLayoutUserImage);
            userName = itemView.findViewById(R.id.rowLayoutUserName);
            postContent = itemView.findViewById(R.id.rowLayoutPostContent);
        }
    }

    public void setData(ArrayList<Integer> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
