package com.example.warrantyvault.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "warranty_items")
public class WarrantyItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String productName;

    public long purchaseDate;

    public int warrantyMonths;

    public long expiryDate;

    public String imageUri;

    public String notes;
    private String imagePath;

    public WarrantyItem(String productName,
                        long purchaseDate,
                        int warrantyMonths,
                        long expiryDate,
                        String imagePath) {

        this.productName = productName;
        this.purchaseDate = purchaseDate;
        this.warrantyMonths = warrantyMonths;
        this.expiryDate = expiryDate;
        this.imagePath = imagePath;

    }
    public String getProductName() {
        return productName;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public long getExpiryDate() {
        return expiryDate;
    }
    public String getImagePath() {
        return imagePath;
    }
}


// package com.example.warrantyvault.model;

// import androidx.room.Entity;
// import androidx.room.PrimaryKey;

// @Entity(tableName = "warranty_items")
// public class WarrantyItem {

//     @PrimaryKey(autoGenerate = true)
//     public int id;

//     public String productName;

//     // Store date as timestamp (better than String)
//     public long purchaseDate;

//     public int warrantyMonths;

//     // Calculated expiry date
//     public long expiryDate;

//     // Path of receipt image
//     public String imageUri;

//     public String notes;

//     public WarrantyItem(String productName,
//                         long purchaseDate,
//                         int warrantyMonths,
//                         long expiryDate,
//                         String imageUri,
//                         String notes) {

//         this.productName = productName;
//         this.purchaseDate = purchaseDate;
//         this.warrantyMonths = warrantyMonths;
//         this.expiryDate = expiryDate;
//         this.imageUri = imageUri;
//         this.notes = notes;
//     }
// }


// package com.example.warrantyvault.model;

// import androidx.room.Entity;
// import androidx.room.PrimaryKey;

// @Entity(tableName = "warranty_items")
// public class WarrantyItem {

//     @PrimaryKey(autoGenerate = true)
//     public int id;

//     public String productName;
//     public String purchaseDate;
//     public int warrantyMonths;

//     public WarrantyItem(String productName, String purchaseDate, int warrantyMonths) {
//         this.productName = productName;
//         this.purchaseDate = purchaseDate;
//         this.warrantyMonths = warrantyMonths;
//     }
// }
