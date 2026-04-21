package com.example.canteen_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PesananAktifActivity extends AppCompatActivity {
    //    Mendeklarasikan variabel
    private RecyclerView rvPesanan;
    private View indicatorAktif, indicatorSelesai;
    private TextView tvAktif, tvSelesai, tvEmpty;
    private PesananAdapter adapter;
    private boolean currentTabIsSelesai = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_aktif);

//        Manipulasi tombol kembali
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goToBeranda();
            }
        });

//        Tangkap komponen berdasarkan ID
        rvPesanan = findViewById(R.id.rvPesanan);
        indicatorAktif = findViewById(R.id.indicatorAktif);
        indicatorSelesai = findViewById(R.id.indicatorSelesai);
        tvAktif = findViewById(R.id.tvAktif);
        tvSelesai = findViewById(R.id.tvSelesai);
        tvEmpty = findViewById(R.id.tvEmptyState);

        rvPesanan.setLayoutManager(new LinearLayoutManager(this));

        // Tombol Kembali
        findViewById(R.id.imgKrjArrowLeft).setOnClickListener(v -> {
            Intent intent = new Intent(PesananAktifActivity.this, BerandaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish(); // Menutup halaman PesananAktif
        });

        //Tampilkan tab Aktif
        showTabContent(false); // false berarti isFinished = false (Aktif)

        // Listener Tab
        findViewById(R.id.tabAktif).setOnClickListener(v -> showTabContent(false));
        findViewById(R.id.tabSelesai).setOnClickListener(v -> showTabContent(true));
    }

//    Fungsi tombol kembali
private void goToBeranda() {
    Intent intent = new Intent(PesananAktifActivity.this, BerandaActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(intent);
    finish();
}

    //    Kalau dari halaman lain mau ke beranda
    @Override
    protected void onResume() {
        super.onResume();
        showTabContent(currentTabIsSelesai);
    }

    private void showTabContent(boolean isSelesai) {
        if (!isSelesai) {
//            UI kalau aktif
            indicatorAktif.setVisibility(View.VISIBLE);
            indicatorSelesai.setVisibility(View.INVISIBLE);
            tvAktif.setTextColor(getResources().getColor(R.color.primary));
            tvSelesai.setTextColor(Color.GRAY);
        } else {
//            UI kalau selesai
            indicatorAktif.setVisibility(View.INVISIBLE);
            indicatorSelesai.setVisibility(View.VISIBLE);
            tvSelesai.setTextColor(getResources().getColor(R.color.primary));
            tvAktif.setTextColor(Color.GRAY);
        }

        // Filter Data per Menu
        filterDataPerMenu(isSelesai);
    }

    //    Filter status pemesanan
    private void filterDataPerMenu(boolean statusCari) {
        List<Menu> listTampil = new ArrayList<>();

        // Ambil riwayat order dari CartManager
        List<Order> history = CartManager.getInstance().getHistoryList();

        if (history != null) {
            for (Order order : history) {
                for (Menu item : order.getItems()) {
                    if (item.isFinished() == statusCari) {
                        listTampil.add(item);
                    }
                }
            }
        }

//        Update adapter
        adapter = new PesananAdapter(this, listTampil);
        rvPesanan.setAdapter(adapter);

        // Munculkan pesan jika kosong
        if (listTampil.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText(statusCari ? "Belum ada pesanan selesai" : "Tidak ada pesanan aktif");
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }
}