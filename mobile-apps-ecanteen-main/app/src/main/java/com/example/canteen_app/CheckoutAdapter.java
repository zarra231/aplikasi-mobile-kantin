package com.example.canteen_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {
//Inisialisasi varaibel
    private Context context;
    private List<Menu> listData;
    public CheckoutAdapter(Context context, List<Menu> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Mengarah ke layout kartu checkout yang kita buat sebelumnya
        View v = LayoutInflater.from(context).inflate(R.layout.item_checkout_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu item = listData.get(position);

//        Set tampilan
        holder.tvNama.setText(item.getProductName());
        holder.tvHarga.setText("Rp " + String.format("%,d", item.getProductPrice()));
        holder.tvQty.setText(item.getQty() + "x");
        holder.imgProduk.setImageResource(item.getProductPath());

        // Logika Catatan
        if (item.getNote() != null && !item.getNote().isEmpty()) {
            holder.tvNote.setVisibility(View.VISIBLE);
            holder.tvNote.setText("Catatan: " + item.getNote());
        } else {
//            Kalau tidak ada catatan
            holder.tvNote.setVisibility(View.GONE);
        }
    }

//    Jumlah item
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvQty, tvNote;
        ImageView imgProduk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_checkout);
            tvHarga = itemView.findViewById(R.id.tv_harga_checkout);
            tvQty = itemView.findViewById(R.id.tv_qty_checkout);
            tvNote = itemView.findViewById(R.id.tv_catatan_checkout);
            imgProduk = itemView.findViewById(R.id.img_checkout);
        }
    }
}
