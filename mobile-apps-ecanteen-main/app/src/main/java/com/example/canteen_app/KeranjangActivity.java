package com.example.canteen_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KeranjangActivity extends AppCompatActivity {
//    Deklarasi Variabel
private RecyclerView rvKeranjang;
    private TextView tvHargaTotal, tvEmptyCart;
    private CartAdapter adapter;
    private List<Menu> listKeranjang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_keranjang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Ambil komponen berdasarkan ID
        rvKeranjang = findViewById(R.id.rv_keranjang);
        tvHargaTotal = findViewById(R.id.tvKrjHargaTotal);
        tvEmptyCart = findViewById(R.id.tv_empty_cart);

//        Pindah ke halaman checkout
        findViewById(R.id.btnKrjCheckout).setOnClickListener(v -> {
            if (listKeranjang != null && !listKeranjang.isEmpty()) {
                Intent intent = new Intent(KeranjangActivity.this, CheckoutActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Keranjang Anda masih kosong!", Toast.LENGTH_SHORT).show();
            }});

//        Tombol kembali
        findViewById(R.id.imgKrjArrowLeft).setOnClickListener(v -> finish());

//        Siapkan Cart
        setupCartList();
    }

    private void setupCartList() {
        // Ambil data terbaru dari CartManager
        listKeranjang = CartManager.getInstance().getCartList();

//        Kalau keranjangnya kosong
        if (listKeranjang.isEmpty()) {
            showEmptyState(true);
        } else {
            showEmptyState(false);
            rvKeranjang.setLayoutManager(new LinearLayoutManager(this));

            // Kita gunakan interface listener agar Activity tahu saat Qty berubah
            adapter = new CartAdapter(this, listKeranjang, new CardListener() {

//                Buat update total harga
                @Override
                public void onTotalChanged(int newTotal) {
                    updateTotalHarga();
                    if (listKeranjang == null || listKeranjang.isEmpty()) {
                        showEmptyState(true);
                    }
                }
            });
            rvKeranjang.setAdapter(adapter);
        }
        updateTotalHarga();
    }

//    Update total harga
private void updateTotalHarga() {
    int total = 0;
    if (listKeranjang != null) {
        for (Menu m : listKeranjang) {
            total += (m.getProductPrice() * m.getQty());
        }
    }
    tvHargaTotal.setText("Rp " + String.format("%,d", total));
}

// Cek kalau keranjang kosong
    private void checkIfEmpty() {
        if (listKeranjang.isEmpty()) {
            showEmptyState(true);
        }
    }

//    Tampilan keranjang kosong
    private void showEmptyState(boolean isEmpty) {
        tvEmptyCart.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rvKeranjang.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        findViewById(R.id.bottom_bar).setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
}