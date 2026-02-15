@Database(entities = {WarrantyItem.class}, version = 2)
public abstract class WarrantyDatabase extends RoomDatabase {

    private static WarrantyDatabase instance;

    public abstract WarrantyDao warrantyDao();

    public static synchronized WarrantyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    WarrantyDatabase.class,
                    "warranty_db"
            )
            .fallbackToDestructiveMigration() // prevents crash when version changes
            .build();
        }
        return instance;
    }
}


// package com.example.warrantyvault.database;

// import android.content.Context;

// import androidx.room.Database;
// import androidx.room.Room;
// import androidx.room.RoomDatabase;

// import com.example.warrantyvault.model.WarrantyItem;

// @Database(entities = {WarrantyItem.class}, version = 1)
// public abstract class WarrantyDatabase extends RoomDatabase {

//     private static WarrantyDatabase instance;

//     public abstract WarrantyDao warrantyDao();

//     public static synchronized WarrantyDatabase getInstance(Context context) {
//         if (instance == null) {
//             instance = Room.databaseBuilder(
//                     context.getApplicationContext(),
//                     WarrantyDatabase.class,
//                     "warranty_db"
//             ).allowMainThreadQueries().build();
//         }
//         return instance;
//     }
// }
