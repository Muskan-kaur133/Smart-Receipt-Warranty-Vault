package com.example.warrantyvault;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.warrantyvault.database.WarrantyDatabase;
import com.example.warrantyvault.model.WarrantyItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WarrantyViewModel extends AndroidViewModel {

    private WarrantyDatabase database;
    private LiveData<List<WarrantyItem>> allItems;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public WarrantyViewModel(@NonNull Application application) {
        super(application);
        database = WarrantyDatabase.getInstance(application);
        allItems = database.warrantyDao().getAllItems();
    }

    public LiveData<List<WarrantyItem>> getAllItems() {
        return allItems;
    }

    public void insert(WarrantyItem item) {
        executorService.execute(() -> {
            database.warrantyDao().insert(item);
        });
    }
    public void delete(WarrantyItem item) {
        executorService.execute(() ->
                database.warrantyDao().delete(item)
        );
    }
}
