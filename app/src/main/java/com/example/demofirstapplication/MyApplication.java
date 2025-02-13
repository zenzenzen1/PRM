package com.example.demofirstapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "push_notification_demo_id";
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log and toast
                    Log.d(TAG, token);
                });
//        GoogleCredentials googleCredentials = null;
//        try {
//            googleCredentials = GoogleCredentials
//                    .fromStream(new FileInputStream("service-account.json"))
//                    ;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            googleCredentials.refresh();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        var token = googleCredentials.getAccessToken().getTokenValue();
        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PushNotificationDemo"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}




















