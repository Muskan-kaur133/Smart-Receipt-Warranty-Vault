package com.example.warrantyvault;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.warrantyvault.database.WarrantyDao;
import com.example.warrantyvault.database.WarrantyDatabase;
import com.example.warrantyvault.model.WarrantyItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // DATABASE TEST
        WarrantyDatabase db = WarrantyDatabase.getInstance(this);
        WarrantyDao dao = db.warrantyDao();

        WarrantyItem item = new WarrantyItem(
                "Earphones",
                "2026-02-09",
                12
        );

        dao.insert(item);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}