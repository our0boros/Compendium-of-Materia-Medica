package com.example.compendiumofmateriamedica;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import model.Adapters.NotificationAdapter;
import model.Datastructure.DataType;
import model.Datastructure.GeneratorFactory;
import model.Datastructure.NewEvent;
import model.Datastructure.NewEventHandler;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTree;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;

public class MyApp extends Application {
    private RBTree<User> userTree;
    private RBTree<Plant> plantTree;
    private RBTree<Post> postTree;
    private UserTreeManager userTreeManager;
    private PostTreeManager postTreeManager;
    private PlantTreeManager plantTreeManager;
    private GeneralFunctions generalFunctions;
    private NewEventHandler eventHandler;
    private NotificationAdapter notificationAdapter;
    @Override
    public void onCreate(){
        super.onCreate();

        generalFunctions = GeneralFunctions.getInstance(getBaseContext());
        try {
            userTree = (RBTree<User>) GeneratorFactory.tree(this, DataType.USER, R.raw.users);
            plantTree = (RBTree<Plant>) GeneratorFactory.tree(this, DataType.PLANT, R.raw.plants);
            postTree = (RBTree<Post>) GeneratorFactory.tree(this, DataType.POST, R.raw.posts);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // initialization of singleton instances before any other activities started
        userTreeManager = UserTreeManager.getInstance(userTree);
        postTreeManager = PostTreeManager.getInstance(postTree);
        plantTreeManager = PlantTreeManager.getInstance(plantTree);

        eventHandler = NewEventHandler.getInstance(new ArrayList<>());
        notificationAdapter = NotificationAdapter.getInstance(getApplicationContext(), new ArrayList<>());
        // 注册 EventBus
        EventBus.getDefault().register(this);
//        // 启动全局服务
//        Intent intent = new Intent(this, NotificationService.class);
//        startService(intent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 注销 EventBus
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewLikeEvent(NewEvent event) {
        // only deal with like and comment
        if(event.getEventType() != NewEvent.EventType.POST){
            // 添加事件到 EventHandler 中
            eventHandler.addEvent(event);
            notificationAdapter.getNotifications().add(event);
            notificationAdapter.notifyDataSetChanged();
            // 当通知大于3时，系统推送点赞通知
            Log.d("MyApp", "Unread Noti :" + eventHandler.getUnreadNotifications());
            Log.d("MyApp", "Event List size :" + eventHandler.getEventList().size());
            if(eventHandler.getUnreadNotifications() > 3)
                showNotification("You have " + eventHandler.getUnreadNotifications()  + " unread messages.");
        }
    }
    private void showNotification(String message) {
        // 在这里显示顶部弹出窗口,显示点赞通知
        // 使用 ApplicationContext 代替 ActivityContext
        Context context = getApplicationContext();

        // 可以使用 Toast 或自定义的弹窗来显示
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

//        // 检查通道是否已创建
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            // 请求权限
//            Toast.makeText(context, "Please grant notification permission in settings", Toast.LENGTH_SHORT).show();
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
//        }
        // 已经有权限，可以创建通知渠道
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID", "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
                .setContentTitle("New Like")
                .setContentText(message)
                .setSmallIcon(R.drawable.post_like_btn)
                .setAutoCancel(true)
                .build();
        // 使用 System.currentTimeMillis() 生成唯一的通知 ID
        notificationManager.notify((int) System.currentTimeMillis(), notification); // 1是这个通知的唯一ID
    }
    public NewEventHandler getEventHandler() {
        return eventHandler;
    }
}
