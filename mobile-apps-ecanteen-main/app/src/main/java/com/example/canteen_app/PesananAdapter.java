package com.example.canteen_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.PesananViewHolder> {
//    Inisialisasi Variabel
    private Context context;
    private List<Menu> listMenu;
    public PesananAdapter(Context context, List<Menu> listMenu) {
        this.context = context;
        this.listMenu = listMenu;
    }

    @NonNull
    @Override
    public PesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pesanan, parent, false);
        return new PesananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananViewHolder holder, int position) {
        Menu menu = listMenu.get(position);

        // Set data menu dasar
        holder.imgMenu.setImageResource(menu.getShopImage());
        holder.tvNamaToko.setText(menu.getShopName());
        holder.tvNamaMenu.setText(menu.getProductName() + " (x" + menu.getQty() + ")");

//        Format harga
        int totalHargaItem = menu.getProductPrice() * menu.getQty();
        holder.tvHarga.setText("Rp " + String.format("%,d", menu.getProductPrice() * menu.getQty()).replace(',', '.'));

        // Tampilkan catatan
        if (menu.getNote() != null && !menu.getNote().isEmpty()) {
            holder.tvCatatan.setText("Catatan: " + menu.getNote());
            holder.tvCatatan.setVisibility(View.VISIBLE);
        } else {
            holder.tvCatatan.setVisibility(View.GONE);
        }

        // Set data dari Order Induk
        holder.tvOrderId.setText("ID: #" + menu.getParentOrderId());
        holder.tvWaktuAmbil.setText("Ambil: " + menu.getParentPickupTime());

        // Logika Status
        if (menu.isFinished()) {
            holder.tvStatus.setText("Sudah Diambil");
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else {
//            Kalau pembayaran tunai
            if ("Tunai".equalsIgnoreCase(menu.getPaymentMethod())) {
                holder.tvStatus.setText("Belum Bayar (Bayar di Kasir)");
                holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            } else {
                // Jika QRIS, otomatis sudah bayar tinggal ambil
                holder.tvStatus.setText("Siap Diambil");
                holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
            }
        }

//        untuk pindah ke halaman detail product
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StatusQrisActivity.class);
            // Mengirim seluruh objek menu (Serializable)
            intent.putExtra("ITEM_PESANAN", menu);
            context.startActivity(intent);
        });
    }

//    Mengambil total item
    @Override
    public int getItemCount() {
        return (listMenu != null) ? listMenu.size() : 0;
    }

//    Data recycle
public static class PesananViewHolder extends RecyclerView.ViewHolder {
    ImageView imgMenu;
    TextView tvNamaToko, tvNamaMenu, tvHarga, tvCatatan, tvOrderId, tvWaktuAmbil, tvStatus;

    public PesananViewHolder(@NonNull View itemView) {
        super(itemView);
        imgMenu = itemView.findViewById(R.id.imgMenuPesanan);
        tvNamaToko = itemView.findViewById(R.id.tvNamaTokoPesanan);
        tvNamaMenu = itemView.findViewById(R.id.tvNamaMenu);
        tvHarga = itemView.findViewById(R.id.tvHargaPesanan);
        tvCatatan = itemView.findViewById(R.id.tvCatatanPesanan);
        tvOrderId = itemView.findViewById(R.id.tvIdPesanan);
        tvWaktuAmbil = itemView.findViewById(R.id.tvWaktuAmbil);
        tvStatus = itemView.findViewById(R.id.tvStatusPesanan);
    }
}
}
