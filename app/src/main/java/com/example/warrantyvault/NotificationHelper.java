
    package com.example.warrantyvault;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import org.jspecify.annotations.NonNull;

    public class NotificationHelper {

        public static final String CHANNEL_ID = "warranty_channel";

        public static void createNotificationChannel(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Warranty Notifications";
                String description = "Channel for Warranty Vault notifications";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }
        }

        public static void sendNotification(@NonNull Context applicationContext, String title, String message) {
        }
    }

