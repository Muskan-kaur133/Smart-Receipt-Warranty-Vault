package com.example.warrantyvault;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.util.Calendar;

import com.example.warrantyvault.model.WarrantyItem;

public class AddWarrantyActivity extends AppCompatActivity {

    private WarrantyViewModel viewModel;
    private static final int CAMERA_REQUEST = 101;
    private static final int CAMERA_PERMISSION_CODE = 102;

    private ImageView imagePreview;
    private Bitmap capturedBitmap;
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_warranty);

        imagePreview = findViewById(R.id.imagePreview);
        EditText etProductName = findViewById(R.id.etProductName);
        EditText etWarrantyValue = findViewById(R.id.etWarrantyMonths);
        Spinner spinner = findViewById(R.id.spinnerUnit);
        Button btnSave = findViewById(R.id.btnSave);

        viewModel = new ViewModelProvider(this).get(WarrantyViewModel.class);

        // CAMERA CLICK
        imagePreview.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_CODE);
            } else {
                openCamera();
            }
        });

        // SAVE BUTTON
        btnSave.setOnClickListener(v -> {

            String productName = etProductName.getText().toString().trim();
            String valueText = etWarrantyValue.getText().toString().trim();

            if (productName.isEmpty() || valueText.isEmpty()) {
                Toast.makeText(this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int value;
            try {
                value = Integer.parseInt(valueText);
            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "Enter valid number",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            long purchaseDate = System.currentTimeMillis();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(purchaseDate);

            String unit = spinner.getSelectedItem().toString();

            if (unit.equals("Days")) {
                calendar.add(Calendar.DAY_OF_YEAR, value);
            } else if (unit.equals("Weeks")) {
                calendar.add(Calendar.WEEK_OF_YEAR, value);
            } else if (unit.equals("Months")) {
                calendar.add(Calendar.MONTH, value);
            }else if (unit.equals("Years")){
                calendar.add(Calendar.YEAR,value);
            }


            long expiryDate = calendar.getTimeInMillis();

            WarrantyItem item = new WarrantyItem(
                    productName,
                    purchaseDate,
                    value,
                    expiryDate,imagePath );

            viewModel.insert(item);

            Toast.makeText(this,
                    "Warranty Saved",
                    Toast.LENGTH_SHORT).show();

            finish();
        });
    }

    // OPEN CAMERA
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else {
            Toast.makeText(this,
                    "No camera app found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // HANDLE PERMISSION RESULT
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openCamera();
            } else {
                Toast.makeText(this,
                        "Camera permission denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // GET IMAGE RESULT
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getExtras() != null) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagePreview.setImageBitmap(photo);
            capturedBitmap = photo;
            imagePath = saveImageToInternalStorage(photo);
        }
    }
    private String saveImageToInternalStorage(Bitmap bitmap) {

        try {
            File directory = getApplicationContext().getDir("images", MODE_PRIVATE);
            File file = new File(directory, System.currentTimeMillis() + ".jpg");

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}



//package com.example.warrantyvault;
//
//import android.os.Bundle;
//import android.widget.EditText;
//import android.widget.Button;
//import android.widget.Toast;
//import android.widget.Spinner;
//import android.widget.ImageView;
//import android.widget.ArrayAdapter;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import java.util.Calendar;
//
//import com.example.warrantyvault.model.WarrantyItem;
//
//import android.provider.MediaStore;
//import android.content.Intent;
//import android.graphics.Bitmap;
//
//public class AddWarrantyActivity extends AppCompatActivity {
//
//    private WarrantyViewModel viewModel;
//    private static final int CAMERA_REQUEST = 101;
//    private static final int CAMERA_PERMISSION_CODE = 102;
//    private ImageView imagePreview;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_add_warranty);
//
//        EditText etProductName = findViewById(R.id.etProductName);
//        EditText etWarrantyValue = findViewById(R.id.etWarrantyMonths);
//        Spinner spinner = findViewById(R.id.spinnerUnit);
//        Button btnSave = findViewById(R.id.btnSave);
//        imagePreview = findViewById(R.id.imagePreview);
//
//        viewModel = new ViewModelProvider(this).get(WarrantyViewModel.class);
//
//        // ✅ Setup Spinner (CORRECT PLACE)
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_item,
//                new String[]{"Days", "Weeks", "Months"}
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        // ✅ Open Camera
//        imagePreview.setOnClickListener(v -> {
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(cameraIntent, CAMERA_REQUEST);
//        });
//
//        // ✅ Save Button
//        btnSave.setOnClickListener(v -> {
//
//            String productName = etProductName.getText().toString().trim();
//            String valueText = etWarrantyValue.getText().toString().trim();
//
//            if (productName.isEmpty() || valueText.isEmpty()) {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            int value;
//            try {
//                value = Integer.parseInt(valueText);
//            } catch (NumberFormatException e) {
//                Toast.makeText(this, "Enter valid number", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            long purchaseDate = System.currentTimeMillis();
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(purchaseDate);
//
//            String unit = spinner.getSelectedItem().toString();
//
//            if (unit.equals("Days")) {
//                calendar.add(Calendar.DAY_OF_YEAR, value);
//            } else if (unit.equals("Weeks")) {
//                calendar.add(Calendar.WEEK_OF_YEAR, value);
//            } else if (unit.equals("Months")) {
//                calendar.add(Calendar.MONTH, value);
//            }
//
//            long expiryDate = calendar.getTimeInMillis();
//
//            WarrantyItem item = new WarrantyItem(
//                    productName,
//                    purchaseDate,
//                    value,
//                    expiryDate,
//                    "",
//                    ""
//            );
//
//            viewModel.insert(item);
//
//            Toast.makeText(this, "Warranty Saved", Toast.LENGTH_SHORT).show();
//            finish();
//        });
//    }
//
//    // ✅ MUST BE OUTSIDE onCreate
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imagePreview.setImageBitmap(photo);
//        }
//    }
//}