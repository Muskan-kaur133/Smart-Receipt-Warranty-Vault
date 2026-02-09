package com.example.warrantyvault.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "warranty_items")
public class WarrantyItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String productName;
    public String purchaseDate;
    public int warrantyMonths;

    public WarrantyItem(String productName, String purchaseDate, int warrantyMonths) {
        this.productName = productName;
        this.purchaseDate = purchaseDate;
        this.warrantyMonths = warrantyMonths;
    }
}