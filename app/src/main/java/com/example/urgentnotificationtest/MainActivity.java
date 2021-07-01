package com.example.urgentnotificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("notification clicked");
                runNotification();
//                Intent fullScreenIntent = new Intent(this, MainActivity.class);
//                PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
//                        fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, 0)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setFullScreenIntent(fullScreenPendingIntent, true);

            }
        });
    }

    public void showToast(String text){
        Log.d("UrgentNotificationTest", text);
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void runNotification(){
        showToast("runNotification");
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                // send notification to NotificationManager
                showToast("runNotification >> inside timer");
                Intent fullScreenIntent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                     fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder notificationBuilder =new Notification.Builder(getApplicationContext());
                notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("title")
                        .setContentText("content")
//                .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setFullScreenIntent(fullScreenPendingIntent, true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
                    showToast("runNotification >> insider timer >> version check");
                }

                NotificationManager notificationManager =
                        (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                    notificationBuilder.setChannelId(channelId);
                }


                notificationManager.notify(0, notificationBuilder.build());
            }
        }, 5000);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}