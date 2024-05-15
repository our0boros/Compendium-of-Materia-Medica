package com.example.compendiumofmateriamedica;

import android.Manifest;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

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
/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description: Loading data here will make them persistent.
 * Services will also start here.
 */
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
        // register EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // unregister EventBus
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewLikeEvent(NewEvent event) {
        // only deal with like and comment
        if(event.getEventType() != NewEvent.EventType.POST){
            // add event into EventHandler
            eventHandler.addEvent(event);
            notificationAdapter.getNotifications().add(event);
            notificationAdapter.notifyDataSetChanged();
            // Activate system notification when unread messages is more than 3
            Log.d("MyApp", "Unread Noti :" + eventHandler.getUnreadNotifications());
            Log.d("MyApp", "Event List size :" + eventHandler.getEventList().size());
            if(eventHandler.getUnreadNotifications() > 3)
                showNotification("You have " + eventHandler.getUnreadNotifications()  + " unread messages.");
        }
    }
    private void showNotification(String message) {
        // show window on top

        Context context = getApplicationContext();
        // create notification channel
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
        // use System.currentTimeMillis() generate unique noti ID
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }
    public NewEventHandler getEventHandler() {
        return eventHandler;
    }
}
