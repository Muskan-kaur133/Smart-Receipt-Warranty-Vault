package com.example.warrantyvault.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.warrantyvault.model.WarrantyItem;

import java.util.List;

@Dao
public interface WarrantyDao {

    @Insert
    void insert(WarrantyItem item);

    @Query("SELECT * FROM warranty_items")
    List<WarrantyItem> getAllItems();
}
