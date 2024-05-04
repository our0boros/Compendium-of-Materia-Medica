package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.ui.home.PhotoDialogFragment;

import java.util.List;

/**
 * @author: Xing Chen
 * @datetime: 2024/5/2
 * @description: A post adapter for showing posts
 * the posts will be shown in separate view holders
 * each post is arranged using post_item.xml
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context context;
    // a list of posts
    private List<Post> postsList;
    private FragmentManager fragmentManager;
    // an inner class to hold and reuse the view
    public static class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView userAvatar;
        public TextView content;
        public ImageView photo;

        public PostViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.post_user_name);
            userAvatar = itemView.findViewById(R.id.post_user_avatar);
            content = itemView.findViewById(R.id.post_content);
            photo = itemView.findViewById(R.id.post_photo);
        }
    }

    public PostAdapter(Context context, List<Post> postsList, FragmentManager fragmentManager){
        this.context = context;
        this.postsList = postsList;
        this.fragmentManager = fragmentManager;
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
        // get current post
        Post post = postsList.get(position);
        // get uid of this post user
        int uid = post.getUid();
        // get the user using this uid
        User postUser = MainActivity.findUserById(uid);
        // get the user name,the user's avatar and the post photo
        if (postUser != null){
            String postUserUsername = postUser.getName();
            String postUserAvatarURL = postUser.getAvatar();
            String postPhotoURL = post.getPhoto();
            String postContent = post.getContent();

            // 设置头像点击事件
            holder.userAvatar.setOnClickListener(v -> {
                PhotoDialogFragment avatarDialogFragment = PhotoDialogFragment.newInstance(postUserAvatarURL);
                avatarDialogFragment.show(fragmentManager, "avatar_dialog");
            });
            // 设置照片点击事件
            holder.photo.setOnClickListener(v -> {
                PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(postPhotoURL);
                photoDialogFragment.show(fragmentManager, "photo_dialog");
            });

            // set the content of the viewHolder
            // load avatar image from url
            MainActivity.loadImageFromURL(this.context, postUserAvatarURL, holder.userAvatar, "Avatar");
            holder.username.setText(postUserUsername);
            holder.content.setText(postContent);
            // load photo from post
            MainActivity.loadImageFromURL(this.context, postPhotoURL, holder.photo, "Photo");
        } else {
            String postPhotoURL = post.getPhoto();

            // 如果用户不存在，不做任何操作
            holder.userAvatar.setOnClickListener(v -> {
                Toast.makeText(context, "No user info available", Toast.LENGTH_SHORT).show();
            });

            holder.username.setText("Unknown User");
            holder.content.setText(post.getContent());
            holder.userAvatar.setImageResource(R.drawable.unknown_user);
            MainActivity.loadImageFromURL(this.context, postPhotoURL, holder.photo, "Photo");
        }

    }

    public int getItemCount(){
        return postsList.size();
    }
    // a method to update the posts and change the display
    public void setPosts(List<Post> posts){
        this.postsList = posts;
        notifyDataSetChanged();
    }
}
