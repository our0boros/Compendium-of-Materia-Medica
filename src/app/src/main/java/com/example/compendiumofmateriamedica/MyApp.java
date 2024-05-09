package com.example.compendiumofmateriamedica;

import android.Manifest;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.events.EventHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import model.Datastructure.NewEvent;
import model.Datastructure.NewEventHandler;

public class MyApp extends Application {
    private NewEventHandler eventHandler;
    @Override
    public void onCreate(){
        super.onCreate();

        eventHandler = NewEventHandler.getInstance(new ArrayList<>());

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
        // 显示点赞通知
        showNotification(event.getEventUser().getUsername() + " liked your post (Post id: " + event.getPostId() + ")");
        // 添加事件到 EventHandler 中
        eventHandler.addEvent(event);
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
                .setSmallIcon(R.drawable.button_post_unlike)
                .setAutoCancel(true)
                .build();
        // 使用 System.currentTimeMillis() 生成唯一的通知 ID
        notificationManager.notify((int) System.currentTimeMillis(), notification); // 1是这个通知的唯一ID
    }
    public NewEventHandler getEventHandler() {
        return eventHandler;
    }
}
