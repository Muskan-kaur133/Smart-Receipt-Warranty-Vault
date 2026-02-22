package com.example.warrantyvault;

 // make sure this matches your app package

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.warrantyvault.NotificationHelper;
import com.example.warrantyvault.database.WarrantyDatabase;
import com.example.warrantyvault.model.WarrantyItem;

import java.util.List;

public class ExpiryNotificationWorker extends Worker {

    public ExpiryNotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("ExpiryCheck", "Worker is running!"); // This helps to check if worker runs

        // Get database instance
        WarrantyDatabase db = WarrantyDatabase.getInstance(getApplicationContext());
        List<WarrantyItem> items = db.warrantyDao().getAllItemsSync(); // Make sure you have a synchronous DAO method

        long now = System.currentTimeMillis();

        for (WarrantyItem item : items) {

            long oneDayBefore = item.getExpiryDate() - (24*60 * 60 * 1000); // 1 day in millis

            if (now >= oneDayBefore && now < item.getExpiryDate()) {
                // Send notification
                String title = "Warranty Expiry Reminder";
                String message = "Your warranty for " + item.getProductName() + " expires tomorrow!";
                NotificationHelper.sendNotification(getApplicationContext(), title, message);
            }
        }

        return Result.success();
    }
}