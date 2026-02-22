package com.example.warrantyvault;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.warrantyvault.database.WarrantyDatabase;
import com.example.warrantyvault.database.WarrantyDao;
import com.example.warrantyvault.model.WarrantyItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WarrantyViewModel extends AndroidViewModel {

    private WarrantyDao warrantyDao;
    private LiveData<List<WarrantyItem>> allItems;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public WarrantyViewModel(@NonNull Application application) {
        super(application);

        WarrantyDatabase database = WarrantyDatabase.getInstance(application);
        warrantyDao = database.warrantyDao();
        allItems = warrantyDao.getAllItems();
    }

    public LiveData<List<WarrantyItem>> getAllItems() {
        return allItems;
    }

    // ✅ INSERT
    public void insert(WarrantyItem item) {
        executorService.execute(() -> warrantyDao.insert(item));
    }

    // ✅ DELETE
    public void delete(WarrantyItem item) {
        executorService.execute(() -> warrantyDao.delete(item));
    }

    // ✅ SORT METHODS
    public LiveData<List<WarrantyItem>> sortByExpiryAsc() {
        return warrantyDao.sortByExpiryAsc();
    }

    public LiveData<List<WarrantyItem>> sortByExpiryDesc() {
        return warrantyDao.sortByExpiryDesc();
    }

    public LiveData<List<WarrantyItem>> sortByNameAsc() {
        return warrantyDao.sortByNameAsc();
    }

    public LiveData<List<WarrantyItem>> sortByNameDesc() {
        return warrantyDao.sortByNameDesc();
    }
}