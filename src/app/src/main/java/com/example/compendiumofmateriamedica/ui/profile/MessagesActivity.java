package com.example.compendiumofmateriamedica.ui.profile;

import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import model.Datastructure.NewEvent;
import com.example.compendiumofmateriamedica.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import model.Adapters.NotificationAdapter;
import model.Datastructure.NewEventHandler;
import model.Datastructure.User;
/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description: Show all unread messages
 */
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

        // get current user
        currentUser = (User) this.getIntent().getSerializableExtra("CurrentUser");
        // get singleton
        eventHandler = NewEventHandler.getInstance();
        // get events list
        List<NewEvent> notifications = eventHandler.getEventList();

        page_name=findViewById(R.id.page_name);
        page_name.setText("Messages");
        back=findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            // when start an activity from a fragment, the fragment never got killed
            // simply come back to original fragment by kill the current activity
            @Override
            public void onClick(View view) {
                // empty events list
                eventHandler.markAllEventsAsRead();
                // empty notifications list
                notificationAdapter.setNotifications(new ArrayList<>());
                finish();
            }
        });

        // get RecyclerView
        notificationRecyclerView = findViewById(R.id.messages_recyclerView);
        // create adapter for it
        notificationAdapter = NotificationAdapter.getInstance();
        notificationRecyclerView.setAdapter(notificationAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notificationRecyclerView.setLayoutManager(layoutManager);
    }
}