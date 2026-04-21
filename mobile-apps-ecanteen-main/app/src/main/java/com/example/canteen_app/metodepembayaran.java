package com.example.canteen_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class metodepembayaran extends AppCompatActivity {
    //    Deklarasi Variabel
    RadioGroup rgPembayaran;
    Button btnPilih;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);

//        Tangkap Komponen berdasarkan id
        rgPembayaran = findViewById(R.id.rgPembayaran);
        btnPilih = findViewById(R.id.btnPilih);
        btnBack = findViewById(R.id.btnBack);

        // tombol back
        btnBack.setOnClickListener(view -> {
            finish();
        });

        // tombol pilih metode
        btnPilih.setOnClickListener(view -> {
            int selectedId = rgPembayaran.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(
                        metodepembayaran.this,
                        "Silakan pilih metode pembayaran",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
//                Metode Pembayaran
                String metode = (selectedId == R.id.rbQris) ? "QRIS" : "Tunai";
//                Data jam
                String jamAmbil = getIntent().getStringExtra("PICKUP_TIME");

//                Pemilihan Qris atau Tunai
                if (metode.equals("QRIS")) {
                    // Alur QRIS: Tetap ke halaman delay Qris
                    Intent intent = new Intent(metodepembayaran.this, Qris.class);
                    intent.putExtra("METODE_BAYAR", metode);
                    intent.putExtra("PICKUP_TIME", jamAmbil);
                    startActivity(intent);
                } else {
                    // Alur Tunai Langsung proses simpan data
                    prosesPesananTunai(metode, jamAmbil);
                }
            }
        });
    }

    // Untuk pembayaran cash
    private void prosesPesananTunai(String metode, String jam) {

        // Ambil data dari keranjang
        List<Menu> keranjangSekarang = CartManager.getInstance().getCartList();

        // Tandai setiap menu dengan metode bayar Tunai
        for (Menu m : keranjangSekarang) {

//            Buat ID unik
            String idOrderIndividu = "INV-" + System.currentTimeMillis() + "-" + m.getProductId();

//            Set data pembayaran tunai
            m.setParentOrderId(idOrderIndividu);
            m.setParentPickupTime(jam);
            m.setPaymentMethod(metode);

//                Set pesanan menjadi per item
            List<Menu> itemTunggal = new ArrayList<>();
            itemTunggal.add(m);

//            Hitung harga per item
            int totalHargaItem = m.getProductPrice() * m.getQty();

//            Simpan jadi objek sendiri
            Order orderBaru = new Order(idOrderIndividu, itemTunggal, jam, totalHargaItem, metode);
            CartManager.getInstance().addOrderToHistory(orderBaru);
        }

//        Bersihkan keranjang
        CartManager.getInstance().clearCart();

//        Pindah ke halaman aktif
        Intent intent = new Intent(metodepembayaran.this, PesananAktifActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//          Toast
        Toast.makeText(this, "Pesanan Berhasil! Silakan bayar di toko masing-masing.", Toast.LENGTH_LONG).show();
        finish();
    }
}
