package model.Adapters;

//import android.app.Notification;
import static model.UtilsApp.loadImageFromURL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compendiumofmateriamedica.MainActivity;
import model.Datastructure.NewEvent;
import com.example.compendiumofmateriamedica.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Datastructure.User;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    //这里放一个变量存储通知
    private List<NewEvent> notifications = new ArrayList<>();
    private Context context;
    private static NotificationAdapter instance;

    // 为 RecyclerView 创建新的 ViewHolder
    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView userAvatar;
        public TextView userActivity;
        public ImageView userActivityIcon;
        public TextView userActivityTime;

        // 绑定一下ui元素和变量
        public NotificationViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.message_username);
            userAvatar = itemView.findViewById(R.id.message_user_avtar);
            userActivity = itemView.findViewById(R.id.message_user_activity);
            userActivityIcon = itemView.findViewById(R.id.message_ic_like);
            userActivityTime = itemView.findViewById(R.id.message_user_activity_time);
        }
    }
    private NotificationAdapter(Context context, List<NewEvent> notifications) {
        this.context = context;
        this.notifications = notifications;
    }
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
    @NonNull
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new NotificationAdapter.NotificationViewHolder(v);
    }
    // 将数据绑定到 ViewHolder 上
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NewEvent event = notifications.get(position);
        User user = event.getEventUser();

        // 设置用户名
        holder.username.setText(user.getUsername());
        // 设置用户头像
        loadImageFromURL(context, user.getAvatar_url(), holder.userAvatar, "Avatar");

        // 设置用户行为
        if (event.getEventType() == NewEvent.EventType.LIKE) {
            holder.userActivity.setText("liked your post");
            // 设置与行为对应的图标图片
            holder.userActivityIcon.setImageResource(R.drawable.button_post_unlike);
        } else if (event.getEventType() == NewEvent.EventType.COMMENT) {
            holder.userActivity.setText("commented your post");
            // 设置与行为对应的图标图片
            holder.userActivityIcon.setImageResource(R.drawable.ic_comment);
        }

        // 设置行为时间
        holder.userActivityTime.setText(formatTime(event.getEventTime()));

    }

    // 获取通知的数量
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // 更新通知列表,并通知适配器刷新数据
    public void setNotifications(List<NewEvent> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    private String formatTime(Date time) {
        // 这里你可以根据需要格式化时间,例如使用 SimpleDateFormat
        // 这里只是一个简单的示例
        return time.toString();
    }
    public List<NewEvent> getNotifications(){
        return this.notifications;
    }
}
