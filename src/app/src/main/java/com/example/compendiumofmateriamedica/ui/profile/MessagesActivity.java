package com.example.compendiumofmateriamedica.ui.profile;

import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import model.Datastructure.NewEvent;
import com.example.compendiumofmateriamedica.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import model.Adapters.NotificationAdapter;
import model.Datastructure.NewEventHandler;
import model.Datastructure.User;

public class MessagesActivity extends AppCompatActivity {

    private TextView page_name;
    private ImageView back;
    private User currentUser;
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter notificationAdapter;
    private NewEventHandler eventHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // 当前user
        currentUser = (User) this.getIntent().getSerializableExtra("CurrentUser");
        // 获得单例
        eventHandler = NewEventHandler.getInstance();
        // 获得当前通知列表
        List<NewEvent> notifications = eventHandler.getEventList();

        page_name=findViewById(R.id.page_name);
        page_name.setText("Messages");
        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            // when start an activity from a fragment, the fragment never got killed
            // simply come back to original fragment by kill the current activity
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 找到 RecyclerView
        notificationRecyclerView = findViewById(R.id.messages_recyclerView);
        // 创建适配器
        notificationAdapter = NotificationAdapter.getInstance();
        notificationRecyclerView.setAdapter(notificationAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notificationRecyclerView.setLayoutManager(layoutManager);

//        // 获取当前用户的通知列表,并更新适配器
//        int currentUserId = getCurrentUserId();
//        List<Notification> notifications = getNotificationsForUser(currentUserId);
//        notificationAdapter.setNotifications(notifications);


    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onNewLikeEvent(NewEvent event) {
//        // 将新的点赞事件添加到通知列表中
//        notificationAdapter.getNotifications().add(event);
//        notificationAdapter.notifyDataSetChanged();
//
//        eventHandler.addEvent(event);
//    }
//    private List<Notification> getNotificationsForUser(int userId) {
//        List<Notification> notifications = new ArrayList<>();
//        List<Post> posts = PostTreeManager.getInstance().getPostsByUserId(userId);
//        for (Post post : posts) {
//            List<Integer> likes = post.getLikes();
//            for (int likerId : likes) {
//                User likeUser = UserTreeManager.getInstance().getUserById(likerId);
//                String message = likeUser.getUsername() + " 点赞了你的帖子 " + post.getPost_id();
//                notifications.add(new Notification(message));
//            }
//        }
//        return notifications;
//    }
}