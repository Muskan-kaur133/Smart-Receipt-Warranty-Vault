package com.example.warrantyvault;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.warrantyvault.model.WarrantyItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private WarrantyViewModel viewModel;
    private WarrantyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Notification channel
        NotificationHelper.createNotificationChannel(this);

        // RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WarrantyAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemDeleteListener(new WarrantyAdapter.OnItemDeleteListener() {
            @Override
            public void onDeleteClick(WarrantyItem item) {
                viewModel.delete(item);
            }

            @Override
            public void onItemClick(WarrantyItem item) {
                showWarrantyPopup(item);
            }
        });

        viewModel = new ViewModelProvider(this).get(WarrantyViewModel.class);
        viewModel.getAllItems().observe(this, items -> adapter.setWarrantyList(items));

        // Floating action button
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddWarrantyActivity.class);
            startActivity(intent);
        });

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Schedule WorkManager to check expiry daily
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                ExpiryNotificationWorker.class,
                24, TimeUnit.HOURS
        )
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "daily_expiry_check",
                ExistingPeriodicWorkPolicy.KEEP,
                request
        );
    }

    // Search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Search products...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    // Show warranty popup
    private void showWarrantyPopup(WarrantyItem item) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_warranty_details);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        ImageView image = dialog.findViewById(R.id.dialogImage);
        TextView name = dialog.findViewById(R.id.dialogName);
        TextView expiry = dialog.findViewById(R.id.dialogExpiry);
        TextView seller = dialog.findViewById(R.id.dialogSeller);
        TextView price = dialog.findViewById(R.id.dialogPrice);
        TextView serial = dialog.findViewById(R.id.dialogSerial);
        TextView notes = dialog.findViewById(R.id.dialogNotes);
        TextView status = dialog.findViewById(R.id.dialogStatus);

        name.setText(item.getProductName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        expiry.setText("Expires: " + sdf.format(new Date(item.getExpiryDate())));
        seller.setText("Seller: " + item.getSellerName());
        price.setText("Price: " + item.getPrice());
        serial.setText("Serial: " + item.getSerialNumber());
        notes.setText("Notes: " + item.getNotes());

        if (item.getExpiryDate() > System.currentTimeMillis()) {
            status.setText("Active");
            status.setTextColor(Color.parseColor("#2E7D32"));
        } else {
            status.setText("Expired");
            status.setTextColor(Color.parseColor("#C62828"));
        }

        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(item.getImagePath());
            image.setImageBitmap(bitmap);
        }

        dialog.show();
    }
}