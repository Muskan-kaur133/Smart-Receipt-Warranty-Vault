package com.example.warrantyvault;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warrantyvault.database.WarrantyDatabase;
import com.example.warrantyvault.model.WarrantyItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditWarrantyActivity extends AppCompatActivity {

    private EditText etProductName, etSellerName, etPrice, etSerial, etNotes;
    private TextView tvExpiryDate;
    private Button btnUpdate;

    private WarrantyItem currentItem;
    private WarrantyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_warranty);

        // Initialize views
        etProductName = findViewById(R.id.etProductName);
        etSellerName = findViewById(R.id.etSellerName);
        etPrice = findViewById(R.id.etPrice);
        etSerial = findViewById(R.id.etSerial);
        etNotes = findViewById(R.id.etNotes);
        tvExpiryDate = findViewById(R.id.tvExpiryDate);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = WarrantyDatabase.getInstance(this);

        // Get item from intent
        currentItem = (WarrantyItem) getIntent().getSerializableExtra("item");

        if (currentItem != null) {

            // Set existing values
            etProductName.setText(currentItem.getProductName());
            etSellerName.setText(currentItem.getSellerName());
            etPrice.setText(currentItem.getPrice());
            etSerial.setText(currentItem.getSerialNumber());
            etNotes.setText(currentItem.getNotes());

            String formattedDate = new SimpleDateFormat(
                    "dd MMM yyyy, hh:mm a",
                    Locale.getDefault()
            ).format(new Date(currentItem.getExpiryDate()));

            tvExpiryDate.setText(formattedDate);
        }

        // Update button
        btnUpdate.setOnClickListener(v -> {

            currentItem.setProductName(etProductName.getText().toString());
            currentItem.setSellerName(etSellerName.getText().toString());
            currentItem.setPrice(etPrice.getText().toString());
            currentItem.setSerialNumber(etSerial.getText().toString());
            currentItem.setNotes(etNotes.getText().toString());

            // Update in background thread
            new Thread(() -> {
                db.warrantyDao().update(currentItem);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });

            }).start();
        });
    }
}

//package com.example.warrantyvault;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.warrantyvault.database.WarrantyDatabase;
//import com.example.warrantyvault.model.WarrantyItem;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class EditWarrantyActivity extends AppCompatActivity {
//
//    private EditText etProductName;
//    private TextView tvExpiryDate;
//    private Button btnUpdate;
//
//    private WarrantyItem currentItem;
//    private WarrantyDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_warranty);
//
//        EditText etProductName = findViewById(R.id.etProductName);
//        EditText etSellerName = findViewById(R.id.etSellerName);
//        EditText etPrice = findViewById(R.id.etPrice);
//        EditText etSerial = findViewById(R.id.etSerial);
//        EditText etNotes = findViewById(R.id.etNotes);
//        Button btnUpdate = findViewById(R.id.btnUpdate);
//
//        db = WarrantyDatabase.getInstance(this);
//
//        currentItem = (WarrantyItem) getIntent().getSerializableExtra("item");
//        etProductName.setText(currentItem.getProductName());
//        etSellerName.setText(currentItem.getSellerName());
//        etPrice.setText(currentItem.getPrice());
//        etSerial.setText(currentItem.getSerialNumber());
//        etNotes.setText(currentItem.getNotes());
//
//        if (currentItem != null) {
//            etProductName.setText(currentItem.getProductName());
//
//            String formattedDate = new SimpleDateFormat(
//                    "dd MMM yyyy, hh:mm a",
//                    Locale.getDefault()
//            ).format(new Date(currentItem.getExpiryDate()));
//
//            tvExpiryDate.setText(formattedDate);
//        }
//
//        btnUpdate.setOnClickListener(v -> {
//
//            currentItem.setProductName(etProductName.getText().toString());
//            currentItem.setSellerName(etSellerName.getText().toString());
//            currentItem.setPrice(etPrice.getText().toString());
//            currentItem.setSerialNumber(etSerial.getText().toString());
//            currentItem.setNotes(etNotes.getText().toString());
//
//            viewModel.update(currentItem);
//
//            finish();
//        });
//
//            currentItem.setProductName(updatedName);
//
//            new Thread(() -> {
//                db.warrantyDao().update(currentItem);
//
//                runOnUiThread(() -> {
//                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
//                    finish();
//                });
//            }).start();
//        });
//    }
//}
