package com.example.warrantyvault;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warrantyvault.model.WarrantyItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.widget.Filter;
import android.widget.Filterable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class WarrantyAdapter extends RecyclerView.Adapter<WarrantyAdapter.ViewHolder> implements Filterable {

    private List<WarrantyItem> warrantyList = new ArrayList<>();
    private List<WarrantyItem> warrantyListFull = new ArrayList<>();

    public void setWarrantyList(List<WarrantyItem> list) {
        this.warrantyList = new ArrayList<>(list);
        this.warrantyListFull = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return warrantyFilter;
    }

    private final Filter warrantyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<WarrantyItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(warrantyListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (WarrantyItem item : warrantyListFull) {
                    if (item.getProductName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            warrantyList.clear();
            warrantyList.addAll((List<WarrantyItem>) results.values);
            notifyDataSetChanged();
        }
    };

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

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        holder.tvExpiry.setText("Expires: " + sdf.format(new Date(item.getExpiryDate())));

        if (item.getExpiryDate() > System.currentTimeMillis()) {
            holder.tvStatus.setText("● Active");
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
        } else {
            holder.tvStatus.setText("Expired");
            holder.tvStatus.setTextColor(Color.parseColor("#C62828"));
        }

        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(item.getImagePath());
            holder.itemImage.setImageBitmap(bitmap);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onDeleteClick(item);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return warrantyList.size();
    }

    public interface OnItemDeleteListener {
        void onDeleteClick(WarrantyItem item);
        void onItemClick(WarrantyItem item);
    }

    private OnItemDeleteListener listener;

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.listener = listener;
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