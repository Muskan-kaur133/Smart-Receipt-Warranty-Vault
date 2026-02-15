package com.example.warrantyvault;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.warrantyvault.model.WarrantyItem;

public class AddWarrantyActivity extends AppCompatActivity {

    private WarrantyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_warranty);

        EditText etProductName = findViewById(R.id.etProductName);
        EditText etWarrantyValue = findViewById(R.id.etWarrantyMonths);
        Spinner spinner = findViewById(R.id.spinnerUnit);

        Button btnSave = findViewById(R.id.btnSave);

        viewModel = new ViewModelProvider(this).get(WarrantyViewModel.class);

        btnSave.setOnClickListener(v -> {

            String productName = etProductName.getText().toString().trim();
            String valueText = etWarrantyValue.getText().toString().trim();

            if (productName.isEmpty() || valueText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int value = Integer.parseInt(valueText);
            long purchaseDate = System.currentTimeMillis();
            long expiryDate = 0;

            String unit = spinner.getSelectedItem().toString();

            if (unit.equals("Days")) {
                expiryDate = purchaseDate + (value * 24L * 60 * 60 * 1000);
            }
            else if (unit.equals("Weeks")) {
                expiryDate = purchaseDate + (value * 7L * 24 * 60 * 60 * 1000);
            }
            else if (unit.equals("Months")) {
                expiryDate = purchaseDate + (value * 30L * 24 * 60 * 60 * 1000);
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    new String[]{"Days", "Weeks", "Months"}
            );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            WarrantyItem item = new WarrantyItem(
                    productName,
                    purchaseDate,
                    value,
                    expiryDate,
                    "",
                    ""
            );

            viewModel.insert(item);

            Toast.makeText(this, "Warranty Saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}