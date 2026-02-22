package com.example.warrantyvault.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.warrantyvault.model.WarrantyItem;

import java.util.List;

@Dao
public interface WarrantyDao {

    // Insert new item
    @Insert
    void insert(WarrantyItem item);

    // Update item
    @Update
    void update(WarrantyItem item);

    // Delete single item
    @Delete
    void delete(WarrantyItem item);

    // Delete all items
    @Query("DELETE FROM warranty_items")
    void deleteAll();

    // Get all items (NOW LiveData)
    @Query("SELECT * FROM warranty_items ORDER BY expiryDate ASC")
    LiveData<List<WarrantyItem>> getAllItems();

    // Get expired items (NOW LiveData)
    @Query("SELECT * FROM warranty_items WHERE expiryDate < :currentDate")
    LiveData<List<WarrantyItem>> getExpiredItems(long currentDate);

    // Get expiring within X days (NOW LiveData)
    @Query("SELECT * FROM warranty_items WHERE expiryDate BETWEEN :currentDate AND :futureDate")
    LiveData<List<WarrantyItem>> getExpiringSoon(long currentDate, long futureDate);

    @Query("SELECT * FROM warranty_items ORDER BY expiryDate ASC")
    LiveData<List<WarrantyItem>> sortByExpiryAsc();

    @Query("SELECT * FROM warranty_items ORDER BY expiryDate DESC")
    LiveData<List<WarrantyItem>> sortByExpiryDesc();

    @Query("SELECT * FROM warranty_items ORDER BY productName ASC")
    LiveData<List<WarrantyItem>> sortByNameAsc();

    @Query("SELECT * FROM warranty_items ORDER BY productName DESC")
    LiveData<List<WarrantyItem>> sortByNameDesc();
}


// package com.example.warrantyvault.database;

// import androidx.room.Dao;
// import androidx.room.Delete;
// import androidx.room.Insert;
// import androidx.room.Query;
// import androidx.room.Update;

// import com.example.warrantyvault.model.WarrantyItem;

// import java.util.List;

// @Dao
// public interface WarrantyDao {

//     // Insert new item
//     @Insert
//     void insert(WarrantyItem item);

//     // Update item
//     @Update
//     void update(WarrantyItem item);

//     // Delete single item
//     @Delete
//     void delete(WarrantyItem item);

//     // Delete all items
//     @Query("DELETE FROM warranty_items")
//     void deleteAll();

//     // Get all items
//     @Query("SELECT * FROM warranty_items ORDER BY expiryDate ASC")
//     List<WarrantyItem> getAllItems();

//     // Get expired items
//     @Query("SELECT * FROM warranty_items WHERE expiryDate < :currentDate")
//     List<WarrantyItem> getExpiredItems(long currentDate);

//     // Get expiring within X days
//     @Query("SELECT * FROM warranty_items WHERE expiryDate BETWEEN :currentDate AND :futureDate")
//     List<WarrantyItem> getExpiringSoon(long currentDate, long futureDate);
// }


// package com.example.warrantyvault.database;

// import androidx.room.Dao;
// import androidx.room.Insert;
// import androidx.room.Query;

// import com.example.warrantyvault.model.WarrantyItem;

// import java.util.List;

// @Dao
// public interface WarrantyDao {

//     @Insert
//     void insert(WarrantyItem item);

//     @Query("SELECT * FROM warranty_items")
//     List<WarrantyItem> getAllItems();
// }
