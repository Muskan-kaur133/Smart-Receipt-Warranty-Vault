package com.example.warrantyvault;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.Dialog;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.widget.Toast;
import android.widget.Filter;
import android.widget.Filterable;
import com.example.warrantyvault.model.WarrantyItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private WarrantyViewModel viewModel;
    private WarrantyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
        viewModel.getAllItems().observe(this, items -> {
            adapter.setWarrantyList(items);
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddWarrantyActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // ✅ SEARCH BAR CODE GOES HERE
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_sort_expiry_asc) {
            viewModel.sortByExpiryAsc().observe(this, items ->
                    adapter.setWarrantyList(items));
            return true;

        } else if (item.getItemId() == R.id.action_sort_expiry_desc) {
            viewModel.sortByExpiryDesc().observe(this, items ->
                    adapter.setWarrantyList(items));
            return true;

        } else if (item.getItemId() == R.id.action_sort_name_asc) {
            viewModel.sortByNameAsc().observe(this, items ->
                    adapter.setWarrantyList(items));
            return true;

        } else if (item.getItemId() == R.id.action_sort_name_desc) {
            viewModel.sortByNameDesc().observe(this, items ->
                    adapter.setWarrantyList(items));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());        expiry.setText("Expires: " + sdf.format(new Date(item.getExpiryDate())));

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

//package com.example.warrantyvault;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.warrantyvault.database.WarrantyDao;
//import com.example.warrantyvault.database.WarrantyDatabase;
//import com.example.warrantyvault.model.WarrantyItem;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//        // DATABASE TEST
//        WarrantyDatabase db = WarrantyDatabase.getInstance(this);
//        WarrantyDao dao = db.warrantyDao();
//
//        long purchaseDate = System.currentTimeMillis();
//        long expiryDate = purchaseDate + (12L * 30 * 24 * 60 * 60 * 1000);
//
//        WarrantyItem item = new WarrantyItem(
//                "Earphones",
//                purchaseDate,
//                12,
//                expiryDate,
//                "",
//                ""
//        );
//
//        dao.insert(item);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}