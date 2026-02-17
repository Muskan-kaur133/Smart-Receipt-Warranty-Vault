package com.example.warrantyvault;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantyvault.model.WarrantyItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class WarrantyAdapter extends RecyclerView.Adapter<WarrantyAdapter.ViewHolder> {

    private List<WarrantyItem> warrantyList = new ArrayList<>();

    public void setWarrantyList(List<WarrantyItem> list) {
        this.warrantyList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_warranty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WarrantyItem item = warrantyList.get(position);

        holder.tvProductName.setText(item.getProductName());

        // Format expiry date
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        String expiryFormatted = sdf.format(new Date(item.getExpiryDate()));

        holder.tvExpiry.setText("Expires: " + expiryFormatted);

        // Status logic
        if (item.getExpiryDate() > System.currentTimeMillis()) {
            holder.tvStatus.setText("Active");
            holder.tvStatus.setTextColor(android.graphics.Color.GREEN);
        } else {
            holder.tvStatus.setText("Expired");
            holder.tvStatus.setTextColor(android.graphics.Color.RED);
        }
        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(item.getImagePath());
            holder.itemImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return warrantyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductName, tvExpiry, tvStatus;
        ImageView itemImage;

        ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvExpiry = itemView.findViewById(R.id.tvExpiry);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}