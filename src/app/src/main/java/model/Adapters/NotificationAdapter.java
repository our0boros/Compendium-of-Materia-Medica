package model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Datastructure.NewEvent;
import model.Datastructure.User;
import static model.UtilsApp.loadImageFromURL;
/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description:
 * Adapter for notification showing.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NewEvent> notifications = new ArrayList<>();
    private Context context;

    // Constructor is private to enforce singleton pattern
    private NotificationAdapter(Context context, List<NewEvent> notifications) {
        if (instance != null) {
            throw new IllegalStateException("Instance already created");
        }
        this.context = context;
        this.notifications = notifications;
    }

    // Singleton instance creation with lazy initialization
    private static NotificationAdapter instance;

    public static synchronized NotificationAdapter getInstance(Context context, List<NewEvent> notifications) {
        if (instance == null) {
            instance = new NotificationAdapter(context, notifications);
        }
        return instance;
    }

    public static synchronized NotificationAdapter getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Instance not created. Call getInstance(Context, List<NewEvent>) first.");
        }
        return instance;
    }

    // ViewHolder for the RecyclerView
    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView userAvatar;
        public TextView userActivity;
        public ImageView userActivityIcon;
        public TextView userActivityTime;
        public View divider;

        // Constructor to bind UI elements
        public NotificationViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.message_username);
            userAvatar = itemView.findViewById(R.id.message_user_avtar);
            userActivity = itemView.findViewById(R.id.message_user_activity);
            userActivityIcon = itemView.findViewById(R.id.message_ic_like);
            userActivityTime = itemView.findViewById(R.id.message_user_activity_time);
            divider=itemView.findViewById(R.id.divider);;
        }
    }

    // Inflate the item layout and return a ViewHolder
    @NonNull
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new NotificationViewHolder(v);
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NewEvent event = notifications.get(position);
        User user = event.getEventUser();

        // Set username
        holder.username.setText(user.getUsername());
        // Load user avatar from URL
        loadImageFromURL(context, user.getAvatar_url(), holder.userAvatar, "Avatar");

        // Set user activity and corresponding icon
        if (event.getEventType() == NewEvent.EventType.LIKE) {
            holder.userActivity.setText("liked your post");
            holder.userActivityIcon.setImageResource(R.drawable.post_like_btn);
        } else if (event.getEventType() == NewEvent.EventType.COMMENT) {
            holder.userActivity.setText("commented your post");
            holder.userActivityIcon.setImageResource(R.drawable.ic_comment);
        }

        // Set activity time
        holder.userActivityTime.setText(formatTime(event.getEventTime()));

        // Show divider if the current item is not the last item
        holder.divider.setVisibility(position == notifications.size() - 1 ? View.GONE : View.VISIBLE);;
    }

    // Get total number of notifications
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // Update notifications list and notify adapter to refresh data
    public void setNotifications(List<NewEvent> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    // Format time method (Can be improved based on date formatting needs)
    private String formatTime(Date time) {
        return time.toString();
    }

    // Getter for notifications list
    public List<NewEvent> getNotifications(){
        return this.notifications;
    }

}
