package model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.R;

import java.util.List;

/**
 * @author: Xing Chen
 * @datetime: 2024/5/2
 * @description: A post adapter for showing posts
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    // a list of posts
    private List<Post> postsList;
    // an inner class to hold and reuse the view
    public static class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView uid;
        public TextView content;
        public TextView photo;

        public PostViewHolder(View itemView){
            super(itemView);
            uid = itemView.findViewById(R.id.post_uid);
            content = itemView.findViewById(R.id.post_content);
            photo = itemView.findViewById(R.id.post_photo);
        }
    }

    public PostAdapter(List<Post> postsList){
        this.postsList = postsList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(v);
    }

    /**
     * attach the date onto view holder
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position){
        Post post = postsList.get(position);
        holder.uid.setText(String.valueOf(post.getUid()));
        holder.content.setText(post.getContent());
        holder.photo.setText(post.getPhoto());
    }

    public int getItemCount(){
        return postsList.size();
    }
    public void setPosts(List<Post> posts){
        this.postsList = posts;
    }
}
