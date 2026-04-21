package com.example.canteen_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StatusQrisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_qris);

        // Inisialisasi View dari XML
        ImageView btnBack = findViewById(R.id.imgBackDetail);
        TextView tvNoPesanan = findViewById(R.id.tvDetailNoPesanan);
        TextView tvNamaToko = findViewById(R.id.tvDetailNamaToko);
        TextView tvStatus = findViewById(R.id.tvDetailStatus);
        TextView tvMetode = findViewById(R.id.tvDetailMetode);
        TextView tvCatatan = findViewById(R.id.tvDetailCatatan);
        TextView tvTotal = findViewById(R.id.tvDetailTotal);
        RecyclerView rvItems = findViewById(R.id.rvDetailItems);
        ImageView ivDetailGambarToko = findViewById(R.id.imgDetailToko);

        // Ambil data Menu
        Menu menuData = (Menu) getIntent().getSerializableExtra("ITEM_PESANAN");

        // Tombol Kembali
        btnBack.setOnClickListener(v -> finish());

        if (menuData != null) {
//            Mengambil gambar
            if (ivDetailGambarToko != null) {
                ivDetailGambarToko.setImageResource(menuData.getShopImage());
            }

            // Set Data ke UI
            tvNoPesanan.setText("#ORD-" + menuData.getParentOrderId());
            tvNamaToko.setText(menuData.getShopName());
            String metode = menuData.getPaymentMethod();
            tvMetode.setText(metode);

//            Logika catatan
            if (menuData.getNote() != null && !menuData.getNote().isEmpty()) {
                tvCatatan.setText("Catatan: " + menuData.getNote());
                tvCatatan.setVisibility(View.VISIBLE);
            } else {
                tvCatatan.setVisibility(View.GONE);
            }

            // Logika Status pesanan
            if (menuData.isFinished()) {
                tvStatus.setText("Status: Selesai");
                tvStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                tvStatus.setText("Status: Siap Diambil");
                tvStatus.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            }

            // Hitung Total & Format Rupiah
            int totalBayar = menuData.getQty() * menuData.getProductPrice();
            String formattedTotal = String.format(new Locale("id", "ID"), "%,d", totalBayar).replace(',', '.');
            tvTotal.setText("Rp " + formattedTotal);

            // Setup RecyclerView untuk daftar item di struk
            List<Menu> listSingleItem = new ArrayList<>();
            listSingleItem.add(menuData); // Masukkan item yang dipilih ke list

            rvItems.setLayoutManager(new LinearLayoutManager(this));
            ReceiptAdapter adapter = new ReceiptAdapter(listSingleItem);
            rvItems.setAdapter(adapter);
        }
    }
}