package com.example.canteen_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BerandaActivity extends AppCompatActivity {
//    Deklarasi Variabel
    private RecyclerView rvToko;
    private TokoAdapter adapter;
    private List<Toko> listToko;
    private LinearLayout navOrders, navProfile;
    private ImageView btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beranda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Ambil komponen berdasarkan ID
    rvToko = findViewById(R.id.rv_daftar_toko);
    navOrders = findViewById(R.id.menu_history);
    navProfile = findViewById(R.id.menu_profile);
    btnCart = findViewById(R.id.btn_cart);

//        Panggil navigasi
        setupNavigation();

//    Fetching Data
    prepareData();

//    Inisialisasi RecyclerView Menggunakan GridLayoutManager
    rvToko.setLayoutManager(new GridLayoutManager(this, 2));

//    Menambahkan Padding
        rvToko.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(android.graphics.Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int padding = 10; // Sesuaikan jaraknya
                outRect.left = padding;
                outRect.right = padding;
                outRect.top = padding;
                outRect.bottom = padding;
            }
        });


//    Hubungkan data ke adapter
    adapter = new TokoAdapter(this, listToko);
    rvToko.setAdapter(adapter);

//    Cart Button
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(BerandaActivity.this, KeranjangActivity.class);
            startActivity(intent);
        });
}

// Quick LInk
    private void setupNavigation(){
        navOrders.setOnClickListener(v -> {
            // pindah ke halaman pesanan
             Intent intent = new Intent(BerandaActivity.this, PesananAktifActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);

        });

        navProfile.setOnClickListener(v -> {
// pindah ke halaman profile
             Intent intent = new Intent(BerandaActivity.this, ProfilActivity.class);
             startActivity(intent);
             overridePendingTransition(0, 0);
        });
    }

// Fungsi list toko
private void prepareData() {
    listToko = new ArrayList<>();
    listToko.add(new Toko(1, "Everyday Kitchen", "Kantin UIKA Lantai 2", R.drawable.everyday_kitchen));
    listToko.add(new Toko(2, "Crispy Kitchen", "Kantin UIKA Lantai 2", R.drawable.crispy_kitchen));
    listToko.add(new Toko(3, "Traditional Noodle", "Kantin UIKA Lantai 1", R.drawable.traditional_noodle));
    listToko.add(new Toko(4, "Spice Bowl", "Kantin UIKA Lantai 1", R.drawable.spice_bowl));
}

//    Set Tombol Aktif
    private void setTombolAktif(Button tombolAktif, Button tombolTidakAktif1, Button tombolTidakAktif2) {
// Efek untuk tombol yang DIKLIK (Active)
        tombolAktif.setBackgroundResource(R.drawable.btn_toogle_active);
        tombolAktif.setTextColor(Color.WHITE);

//        Ambil warna dari res
        int warnaPrimary = ContextCompat.getColor(this, R.color.primary);

        // Efek untuk tombol lainnya (Inactive)
        tombolTidakAktif1.setBackgroundResource(R.drawable.btn_toogle_inactive);
        tombolTidakAktif1.setTextColor(warnaPrimary);

        tombolTidakAktif2.setBackgroundResource(R.drawable.btn_toogle_inactive);
        tombolTidakAktif2.setTextColor(warnaPrimary);
    }
}