package com.example.warrantyvault.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
@Entity(tableName = "warranty_items")
public class WarrantyItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String productName;
    public long purchaseDate;
    public int warrantyMonths;
    public long expiryDate;
    public String imagePath;
    public String sellerName;
    public String price;
    public String serialNumber;
    public String notes;

    // Constructor used by Room
    public WarrantyItem(String productName,
                        long expiryDate,
                        long purchaseDate,
                        String sellerName,
                        String price,
                        String serialNumber,
                        String notes,
                        String imagePath) {

        this.productName = productName;
        this.expiryDate = expiryDate;
        this.purchaseDate = purchaseDate;
        this.sellerName = sellerName;
        this.price = price;
        this.serialNumber = serialNumber;
        this.notes = notes;
        this.imagePath = imagePath;
    }

    // Getters

    public String getProductName() {
        return productName;
    }

    public long getPurchaseDate() {
        return purchaseDate;
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

    public String getSellerName() {
        return sellerName;
    }

    public String getPrice() {
        return price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getNotes() {
        return notes;
    }
    private boolean notificationSent;
    public boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }
    // Setters

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
