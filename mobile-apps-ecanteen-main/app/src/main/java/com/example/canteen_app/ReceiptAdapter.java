package com.example.canteen_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
//    Deklarasi variabel
private List<Menu> itemList;

    public ReceiptAdapter(List<Menu> itemList) {
        this.itemList = itemList;
    }

//    Sambungkan item detail pemesanan
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt_product, parent, false);
        return new ViewHolder(v);
}

// Manipulasi object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu item = itemList.get(position);
        holder.tvName.setText(item.getProductName());
        holder.tvQtyPrice.setText(item.getQty() + " x Rp " + String.format("%,d", item.getProductPrice()).replace(',', '.'));

        int subtotal = item.getQty() * item.getProductPrice();
        holder.tvSubtotal.setText("Rp " + String.format("%,d", subtotal).replace(',', '.'));
    }

//    Total Item
    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

//    Komponen Recycler View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQtyPrice, tvSubtotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvReceiptProductName);
            tvQtyPrice = itemView.findViewById(R.id.tvReceiptQtyPrice);
            tvSubtotal = itemView.findViewById(R.id.tvReceiptSubtotal);
        }
    }
}
